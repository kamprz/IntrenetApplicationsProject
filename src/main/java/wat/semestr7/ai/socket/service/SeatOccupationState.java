package wat.semestr7.ai.socket.service;

import org.springframework.stereotype.Component;
import wat.semestr7.ai.socket.service.model.RowCol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    public synchronized boolean lockPlace(Integer concertId, String userId, RowCol rowCol){
        initializeMapsForConcertIfEmpty(concertId);
        usersConnectedToConcertId.put(userId,concertId);

        if(mapByRowCol.get(concertId).putIfAbsent(rowCol, true) == null){
            //tzn ze sie dodalo
            mapByUserId.get(concertId).putIfAbsent(userId, new HashSet<>());
            mapByUserId.get(concertId).get(userId).add(rowCol);
            return true;
        }
        else return false;
    }

    public synchronized void unlockPlace(Integer concertId, String userId, RowCol rowCol){
        mapByRowCol.get(concertId).remove(rowCol);
        mapByUserId.get(concertId).get(userId).remove(rowCol);
        if(mapByUserId.get(concertId).get(userId).isEmpty()) mapByUserId.get(concertId).remove(userId);
        removeEmptyEntriesIfNeeded(concertId);
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
