package wat.semestr7.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import wat.semestr7.ai.entities.PieceOfMusic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ConcertDetailedDTO
{
    private int concertId;
    private String concertTitle;
    private Date date;
    private List<PieceOfMusicDTO> repertoire;
    private String concertRoomName;
    private String concertRoomAddress;
    private String performersDetails;
    private BigDecimal ticketCost;

    public ConcertDetailedDTO(int concertId, String concertTitle, Date date, String concertRoomName, String concertRoomAddress, String performersDetails, BigDecimal ticketCost) {
        this.concertId = concertId;
        this.concertTitle = concertTitle;
        this.date = date;
        this.concertRoomName = concertRoomName;
        this.concertRoomAddress = concertRoomAddress;
        this.performersDetails = performersDetails;
        this.ticketCost = ticketCost;
    }
}
