package wat.semestr8.tim.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wat.semestr8.tim.entities.Seat;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ConcertRoomDto
{
    private int idConcertRoom;
    private String concertRoomName;
    private String address;
    private BigDecimal rentCosts;
    private List<Seat> seats;
}
