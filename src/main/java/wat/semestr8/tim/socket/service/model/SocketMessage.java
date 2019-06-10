package wat.semestr8.tim.socket.service.model;

import lombok.*;
import wat.semestr8.tim.dtos.SeatDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocketMessage {
    private int concertId;
    private String androidId;
    private List<SeatDto> seat;
    private MessageType type;

    public enum MessageType{
        DISCONNECT,
        FORWARDED,
        LOCKED,
        UNLOCKED
    }
}
