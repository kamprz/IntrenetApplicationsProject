package wat.semestr7.ai.dtos;

import lombok.Data;
import wat.semestr7.ai.entities.ConcertRoom;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ConcertDTO
{
    private int idConcert;
    private String concertTitle;
    private Date date;
    private String concertRoomName;

    public ConcertDTO(int idConcert, String concertTitle, Date date, String concertRoomName) {
        this.idConcert = idConcert;
        this.concertTitle = concertTitle;
        this.date = date;
        this.concertRoomName = concertRoomName;
    }
}
