package wat.semestr8.tim.socket.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import wat.semestr8.tim.dtos.SeatDto;

@Getter
@AllArgsConstructor
@ToString
public class SeatOccupationChangedWsMess {
    public SeatDto seat;
    public MessageType type;
    public String userId;

    public enum MessageType{
        LOCKED,
        UNLOCKED
    }

}
