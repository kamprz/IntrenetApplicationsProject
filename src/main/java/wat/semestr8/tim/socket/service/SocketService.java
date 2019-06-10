package wat.semestr7.ai.socket.service;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.socket.service.model.RowCol;
import wat.semestr7.ai.socket.service.model.SeatOccupationChangedWsMess;

import java.util.Set;

@Service
public class SocketService {
    private SeatOccupationState state;
    private MessageDecoder decoder;

    public SocketService(SeatOccupationState state, MessageDecoder decoder) {
        this.state = state;
        this.decoder = decoder;
    }

    public String seatOccupationChanged(Integer concertId, String messageString){
        SeatOccupationChangedWsMess message = decoder.encode(messageString);
        if(decoder.wasSeatLocked(message)) {
            if(state.lockPlace(concertId, message.getUserId(), new RowCol(message.getRow(), message.getCol())))
            {
                return messageString;
            }
            else return null;
        }
        else {
            state.unlockPlace(concertId,message.getUserId(),new RowCol(message.getRow(),message.getCol()));
            return messageString;
        }
    }

    public String disconnect(Integer concertId, String userId){
        Set<RowCol> unlocked = state.disconnect(concertId, userId);
        StringBuilder message = new StringBuilder("");
        unlocked.forEach(u -> {
            message.append(concertId)
                    .append("_")
                    .append(u.getRow())
                    .append("_")
                    .append(u.getCol())
                    .append("_")
                    .append(decoder.unlockedString)
                    .append("_")
                    .append("id;");
        });
        System.out.println("DISCONNECTING: " + message.toString());
        return message.toString();
    }

    public int getConcertToWhichUserWasConnected(String userId){
        return state.getConcertToWhichUserWasConnected(userId);
    }
}
