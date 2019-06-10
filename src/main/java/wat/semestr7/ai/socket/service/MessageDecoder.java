package wat.semestr7.ai.socket.service;

import org.springframework.stereotype.Component;
import wat.semestr7.ai.socket.service.model.SeatOccupationChangedWsMess;

@Component
public class MessageDecoder {
    public final String lockedString = "t";
    public final String unlockedString = "f";

    public String decode(SeatOccupationChangedWsMess message){
        String type = message.getType().equals(SeatOccupationChangedWsMess.MessageType.LOCKED) ? lockedString : unlockedString;
        return message.getRow() + "_" + message.getCol() + "_" + type + "_" + message.getUserId();
    }

    public SeatOccupationChangedWsMess encode(String string){
        String row,col,type,id;
        int startIdx = 0;
        int endIdx = string.indexOf('_');
        row = string.substring(startIdx,endIdx);

        startIdx = endIdx + 1;
        endIdx = string.indexOf('_',startIdx);
        col = string.substring(startIdx,endIdx);

        startIdx = endIdx + 1;
        endIdx = string.indexOf('_',startIdx);
        type = string.substring(startIdx,endIdx);
        SeatOccupationChangedWsMess.MessageType messageType = type.equals(lockedString)? SeatOccupationChangedWsMess.MessageType.LOCKED : SeatOccupationChangedWsMess.MessageType.UNLOCKED;

        id = string.substring(endIdx+1);

        return new SeatOccupationChangedWsMess(row,col,messageType,id);
    }

    public boolean wasSeatLocked(SeatOccupationChangedWsMess message){
        return message.type.equals(SeatOccupationChangedWsMess.MessageType.LOCKED);
    }
}


//"10_9_t_123456789"