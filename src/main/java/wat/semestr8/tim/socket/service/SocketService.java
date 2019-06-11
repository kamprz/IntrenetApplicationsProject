package wat.semestr8.tim.socket.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.entities.Seat;
import wat.semestr8.tim.model.SeatOccupied;
import wat.semestr8.tim.dtos.SocketMessage;
import wat.semestr8.tim.utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SocketService {

    private HashMap<Integer, HashSet<SeatOccupied>> seatsOccupiedByConcertId = new HashMap<>();
    private HashMap<Integer, HashMap<String, Set<SeatOccupied>>> seatsOccupiedByConcertIdAndUserId = new HashMap<>();
    private HashMap<String, HashSet<Integer>> concertIdByUserId = new HashMap<>();

    private final SimpMessageSendingOperations messagingTemplate;
    @Value("${socket.subscribeAddress}")
    private String subscribeAddress;

    public SocketService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    //gdzie wysylanie informacji z socketa: controller czy service -> raczej service. zrobic tak.

    public SocketMessage seatOccupationChanged(SocketMessage message){
        if(message.getType().equals(SocketMessage.MessageType.LOCKED)) {
            if(lockPlace(message))
            {
                return message;
            }
            else return null;
        }
        else {
            unlockPlace(message);
            return message;
        }
    }

    public SocketMessage disconnect(String androidId, SocketMessage.MessageType messageType, Integer concertId){
        if(messageType.equals(SocketMessage.MessageType.FORWARDED))
        {
            userDisconnectedToBuyTickets(androidId,concertId);
            return null;
        }
        else{
            SocketMessage unlocked = userDisconnectedForGood(androidId,concertId);
            return unlocked;
        }
    }

    public synchronized List<SeatDto> getCurrentlyOccupied(int concertId){
        if(! seatsOccupiedByConcertId.containsKey(concertId))
        {
            return new LinkedList<>();
        }
        else {
            return seatsOccupiedByConcertId.get(concertId).stream()
                    .map(s -> new SeatDto(s.getRow(),s.getCol()))
                    .collect(Collectors.toList());
        }
    }

    public synchronized boolean lockPlace(SocketMessage message){
        SeatDto seat = message.getSeat().get(0);
        SeatOccupied seatOccupied = new SeatOccupied(seat.getRow(),seat.getCol());

        initializeMapsForConcertIfEmpty(message.getConcertId(), message.getAndroidId());
        concertIdByUserId.get(message.getAndroidId()).add(message.getConcertId());

        if(seatsOccupiedByConcertId.get(message.getConcertId()).add(seatOccupied)){
            //tzn ze sie dodalo
            seatsOccupiedByConcertIdAndUserId.get(message.getConcertId()).putIfAbsent(message.getAndroidId(), new HashSet<>());
            seatsOccupiedByConcertIdAndUserId.get(message.getConcertId()).get(message.getAndroidId()).add(seatOccupied);
            seatOccupied.setSocketDisconnectedTime(new Date());
            return true;
        }
        else return false;
    }

    public synchronized void unlockPlace(SocketMessage message){
        Integer concertId = message.getConcertId();
        String userId = message.getAndroidId();
        for(SeatDto seat : message.getSeat()){
            SeatOccupied seatOccupied = new SeatOccupied(seat.getRow(),seat.getCol());
            seatsOccupiedByConcertId.get(concertId).remove(seatOccupied);
            seatsOccupiedByConcertIdAndUserId.get(concertId).get(userId).remove(seatOccupied);
        }
        if(seatsOccupiedByConcertIdAndUserId.get(concertId).get(userId).isEmpty()) seatsOccupiedByConcertIdAndUserId.get(concertId).remove(userId);
        removeEmptyEntriesIfNeeded(concertId,userId);
    }

    public synchronized SocketMessage userDisconnectedForGood(String userId, Integer concertId){
        Set<SeatOccupied> seatsOccupied = seatsOccupiedByConcertIdAndUserId.get(concertId).get(userId);
        if( ! seatsOccupied.isEmpty() ) {
            //cleanUp seatsOccupiedByConcertId
            for(SeatOccupied seatOccupied : seatsOccupied){
                //if(seatsOccupiedByConcertId.get(concertId).removeIf())
                seatsOccupiedByConcertId.get(concertId).remove(seatOccupied);
            }
            //cleanUp seatsOccupiedByConcertIdAndUserId
            seatsOccupiedByConcertIdAndUserId.get(concertId).remove(userId);
            //cleanUp concertIdByUserId
            concertIdByUserId.get(userId).remove(concertId);

            removeEmptyEntriesIfNeeded(concertId,userId);

            //set message
            SocketMessage message = new SocketMessage();
            message.setConcertId(concertId);
            List<SeatDto> seats = new LinkedList<>();
            seatsOccupied.forEach(rc -> seats.add(new SeatDto(rc.getRow(),rc.getCol())));
            message.setSeat(seats);
            message.setType(SocketMessage.MessageType.UNLOCKED);
            return message;
        }
        else {
            removeEmptyEntriesIfNeeded(concertId,userId);
            return null;
        }
    }

    public synchronized void userDisconnectedToBuyTickets(String userId, Integer concertId){
        Set<SeatOccupied> seatsOccupied = seatsOccupiedByConcertIdAndUserId.get(concertId).get(userId);
        for(SeatOccupied seat : seatsOccupied){
            seat.setSocketDisconnectedTime(new Date());
        }
    }

    public synchronized void purchaseFinished(List<Seat> seats, int idConcert, String userID){
        //delete those seats from maps
        for(Seat s : seats){
            SeatOccupied seatOccupied = new SeatOccupied(s.getRow(),s.getPosition());
            if(seatsOccupiedByConcertId.get(idConcert) != null) seatsOccupiedByConcertId.get(idConcert).remove(seatOccupied);
            if(seatsOccupiedByConcertIdAndUserId.get(idConcert) != null
                    && seatsOccupiedByConcertIdAndUserId.get(idConcert).get(userID) != null)
                seatsOccupiedByConcertIdAndUserId.get(idConcert).get(userID).remove(seatOccupied);
        }
    }

    @Scheduled(cron = "* */6 * * * *")
    public synchronized void deleteOccupationsIfNotPaid() {
        Date now = new Date();
        seatsOccupiedByConcertId.forEach((concertId, seatsSet) -> {
            List<SeatDto> seatsToUnlock = new LinkedList<>();
            for(SeatOccupied seat : seatsSet){
                if(DateUtils.minutesBetweenDates(seat.getSocketDisconnectedTime(), now) > 5){
                    seatsToUnlock.add(new SeatDto(seat.getRow(), seat.getCol()));
                }
                SocketMessage message = new SocketMessage();
                message.setConcertId(concertId);
                message.setSeat(seatsToUnlock);
                message.setType(SocketMessage.MessageType.UNLOCKED);
                messagingTemplate.convertAndSend(subscribeAddress+"/"+concertId, message);
            }
        });
    }

    private void initializeMapsForConcertIfEmpty(Integer concertId, String userId){
        seatsOccupiedByConcertId.putIfAbsent(concertId,new HashSet<>());
        seatsOccupiedByConcertIdAndUserId.putIfAbsent(concertId, new HashMap<>());
        seatsOccupiedByConcertIdAndUserId.get(concertId).putIfAbsent(userId,new HashSet<>());
    }

    private void removeEmptyEntriesIfNeeded(Integer concertId, String userId){
        if(seatsOccupiedByConcertId.get(concertId).isEmpty()) seatsOccupiedByConcertId.remove(concertId);
        if(seatsOccupiedByConcertIdAndUserId.get(concertId).get(userId).isEmpty()) seatsOccupiedByConcertIdAndUserId.get(concertId).remove(userId);
        if(seatsOccupiedByConcertIdAndUserId.get(concertId).isEmpty()) seatsOccupiedByConcertIdAndUserId.remove(concertId);
        if(concertIdByUserId.get(userId).isEmpty()) concertIdByUserId.remove(userId);
    }
}
