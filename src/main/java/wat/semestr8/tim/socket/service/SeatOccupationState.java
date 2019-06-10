package wat.semestr8.tim.socket.service;

import com.sun.rowset.internal.Row;
import org.springframework.stereotype.Component;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.socket.service.model.RowCol;
import wat.semestr8.tim.socket.service.model.SocketMessage;

import java.util.*;

@Component
public class SeatOccupationState {
    private HashMap<Integer, HashMap<RowCol,Boolean>> mapByRowCol = new HashMap<>();
    private HashMap<Integer, HashMap<String,Set<RowCol>>> mapByUserId = new HashMap<>();
    private HashMap<String, Integer> usersConnectedToConcertId = new HashMap<>();

    public int getConcertToWhichUserWasConnected(String userId){
        return usersConnectedToConcertId.get(userId);
    }

    public synchronized Set<RowCol> disconnect(Integer concertId, String userId){
        Set<RowCol> placesUnlocked = mapByUserId.get(concertId).get(userId);
        if(placesUnlocked != null){
            for(RowCol rowCol : placesUnlocked){
                mapByRowCol.get(concertId).remove(rowCol);
            }
            mapByUserId.get(concertId).remove(userId);
            removeEmptyEntriesIfNeeded(concertId);
            usersConnectedToConcertId.remove(userId);
        }
        return placesUnlocked;
    }

    public synchronized boolean lockPlace(SocketMessage message){
        SeatDto seat = message.getSeat().get(0);
        RowCol rowCol = new RowCol(seat.getRow(),seat.getCol());
        initializeMapsForConcertIfEmpty(message.getConcertId());
        usersConnectedToConcertId.put(message.getAndroidId(),message.getConcertId());

        if(mapByRowCol.get(message.getConcertId()).putIfAbsent(rowCol, true) == null){
            //tzn ze sie dodalo
            mapByUserId.get(message.getConcertId()).putIfAbsent(message.getAndroidId(), new HashSet<>());
            mapByUserId.get(message.getConcertId()).get(message.getAndroidId()).add(rowCol);
            return true;
        }
        else return false;
    }

    public synchronized void unlockPlace(SocketMessage message){
        Integer concertId = message.getConcertId();
        String androidId = message.getAndroidId();
        for(SeatDto seat : message.getSeat()){
            RowCol rowCol = new RowCol(seat.getRow(),seat.getCol());
            mapByRowCol.get(concertId).remove(rowCol);
            mapByUserId.get(concertId).get(androidId).remove(rowCol);
        }
        if(mapByUserId.get(concertId).get(androidId).isEmpty()) mapByUserId.get(concertId).remove(androidId);
        removeEmptyEntriesIfNeeded(concertId);
    }

    public synchronized SocketMessage userDisconnected(String androidId){
        SocketMessage message = new SocketMessage();
        message.setConcertId(usersConnectedToConcertId.get(androidId));
        Set<RowCol> rowCols = mapByUserId.get(message.getConcertId()).get(androidId);
        for(RowCol rowCol : rowCols){
            mapByRowCol.get(message.getConcertId()).remove(rowCol);
        }
        mapByUserId.get(message.getConcertId()).remove(androidId);
        message.setType(SocketMessage.MessageType.UNLOCKED);

        //posporzatac bardziej

        List<SeatDto> seats = new LinkedList<>();
        rowCols.forEach(rc -> seats.add(new SeatDto(rc.getRow(),rc.getCol())));
        message.setSeat(seats);
        return message;
    }

    private void initializeMapsForConcertIfEmpty(Integer concertId){
        mapByRowCol.putIfAbsent(concertId,new HashMap<>());
        mapByUserId.putIfAbsent(concertId, new HashMap<>());
    }

    private void removeEmptyEntriesIfNeeded(Integer concertId){
        if(mapByUserId.get(concertId).isEmpty()){
            mapByUserId.remove(concertId);
            mapByRowCol.remove(concertId);
        }
    }

    public void printMap(){
        mapByRowCol.get(1).forEach((a,b) -> System.out.println(a));
    }
}
