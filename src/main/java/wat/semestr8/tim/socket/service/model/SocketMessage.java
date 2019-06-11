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

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public List<SeatDto> getSeat() {
        return seat;
    }

    public void setSeat(List<SeatDto> seat) {
        this.seat = seat;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
