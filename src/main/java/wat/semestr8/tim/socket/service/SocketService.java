package wat.semestr8.tim.socket.service;

import org.springframework.stereotype.Service;
import wat.semestr8.tim.socket.service.model.RowCol;
import wat.semestr8.tim.socket.service.model.SeatOccupationChangedWsMess;
import wat.semestr8.tim.socket.service.model.SocketMessage;

import java.util.Set;

@Service
public class SocketService {
    private SeatOccupationState state;

    public SocketService(SeatOccupationState state) {
        this.state = state;
    }

    public SocketMessage seatOccupationChanged(SocketMessage message){
        if(message.getType().equals(SocketMessage.MessageType.LOCKED)) {
            if(state.lockPlace(message))
            {
                return message;
            }
            else return null;
        }
        else {
            state.unlockPlace(message);
            return message;
        }
    }

    public void disconnect(String androidId, SocketMessage.MessageType messageType){
        if(messageType.equals(SocketMessage.MessageType.FORWARDED))
        {
            //delayed removal
        }
        else{

        }

    }

    public int getConcertToWhichUserWasConnected(String userId){
        return state.getConcertToWhichUserWasConnected(userId);
    }
}
