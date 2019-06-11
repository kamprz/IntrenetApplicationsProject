package wat.semestr8.tim.socket.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.entities.Seat;
import wat.semestr8.tim.socket.service.model.SeatOccupied;
import wat.semestr8.tim.socket.service.model.SocketMessage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SocketService {

    private HashMap<Integer, HashSet<SeatOccupied>> rowColsByConcertId = new HashMap<>();
    private HashMap<Integer, HashMap<String, Set<SeatOccupied>>> rowColsByConcertIdAndUserId = new HashMap<>();
    private HashMap<String, HashSet<Integer>> concertIdByUserId = new HashMap<>();

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
            //TODO delayed removal
            userDisconnectedToBuyTickets(androidId,concertId);
            return null;
        }
        else{
            SocketMessage unlocked = userDisconnectedForGood(androidId,concertId);
            return unlocked;
        }

    }

    public synchronized List<SeatDto> getCurrentlyOccupied(int concertId){
        if(! rowColsByConcertId.containsKey(concertId))
        {
            return new LinkedList<>();
        }
        else {
            return rowColsByConcertId.get(concertId).stream()
                    .map(s -> new SeatDto(s.getRow(),s.getCol()))
                    .collect(Collectors.toList());
        }
    }

    public synchronized boolean lockPlace(SocketMessage message){
        SeatDto seat = message.getSeat().get(0);
        SeatOccupied seatOccupied = new SeatOccupied(seat.getRow(),seat.getCol());

        initializeMapsForConcertIfEmpty(message.getConcertId(), message.getAndroidId());
        concertIdByUserId.get(message.getAndroidId()).add(message.getConcertId());

        if(rowColsByConcertId.get(message.getConcertId()).add(seatOccupied)){
            //tzn ze sie dodalo
            rowColsByConcertIdAndUserId.get(message.getConcertId()).putIfAbsent(message.getAndroidId(), new HashSet<>());
            rowColsByConcertIdAndUserId.get(message.getConcertId()).get(message.getAndroidId()).add(seatOccupied);
            return true;
        }
        else return false;
    }

    public synchronized void unlockPlace(SocketMessage message){
        Integer concertId = message.getConcertId();
        String userId = message.getAndroidId();
        for(SeatDto seat : message.getSeat()){
            SeatOccupied seatOccupied = new SeatOccupied(seat.getRow(),seat.getCol());
            rowColsByConcertId.get(concertId).remove(seatOccupied);
            rowColsByConcertIdAndUserId.get(concertId).get(userId).remove(seatOccupied);
        }
        if(rowColsByConcertIdAndUserId.get(concertId).get(userId).isEmpty()) rowColsByConcertIdAndUserId.get(concertId).remove(userId);
        removeEmptyEntriesIfNeeded(concertId,userId);
    }

    public synchronized SocketMessage userDisconnectedForGood(String userId, Integer concertId){
        Set<SeatOccupied> seatsOccupied = rowColsByConcertIdAndUserId.get(concertId).get(userId);
        if( ! seatsOccupied.isEmpty() ) {
            //cleanUp rowColsByConcertId
            for(SeatOccupied seatOccupied : seatsOccupied){
                //if(rowColsByConcertId.get(concertId).removeIf())
                rowColsByConcertId.get(concertId).remove(seatOccupied);
            }
            //cleanUp rowColsByConcertIdAndUserId
            rowColsByConcertIdAndUserId.get(concertId).remove(userId);
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
        Set<SeatOccupied> seatsOccupied = rowColsByConcertIdAndUserId.get(concertId).get(userId);
        for(SeatOccupied seat : seatsOccupied){
            seat.setSocketDisconnectedTime(new Date());
        }
    }

    public synchronized void purchaseFinished(List<Seat> seats, int idConcert, String userID){
        //delete those seats from maps
        for(Seat s : seats){
            SeatOccupied seatOccupied = new SeatOccupied(s.getRow(),s.getCol());
            rowColsByConcertId.get(idConcert).remove(seatOccupied);
            rowColsByConcertIdAndUserId.get(idConcert).get(userID).remove(seatOccupied);
        }
    }

    @Scheduled(cron = "* */3 * * * *")
    public synchronized void deleteOccupationsIfNotPaid() {


    }

    private void initializeMapsForConcertIfEmpty(Integer concertId, String userId){
        rowColsByConcertId.putIfAbsent(concertId,new HashSet<>());
        rowColsByConcertIdAndUserId.putIfAbsent(concertId, new HashMap<>());
        rowColsByConcertIdAndUserId.get(concertId).putIfAbsent(userId,new HashSet<>());

    }

    private void removeEmptyEntriesIfNeeded(Integer concertId, String userId){
        if(rowColsByConcertId.get(concertId).isEmpty()) rowColsByConcertId.remove(concertId);
        if(rowColsByConcertIdAndUserId.get(concertId).get(userId).isEmpty()) rowColsByConcertIdAndUserId.get(concertId).remove(userId);
        if(rowColsByConcertIdAndUserId.get(concertId).isEmpty()) rowColsByConcertIdAndUserId.remove(concertId);
        if(concertIdByUserId.get(userId).isEmpty()) concertIdByUserId.remove(userId);
    }
}
