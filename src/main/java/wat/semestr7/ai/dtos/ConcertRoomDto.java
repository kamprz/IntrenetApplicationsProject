package wat.semestr7.ai.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wat.semestr7.ai.entities.Seat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ConcertRoomDto
{
    private int idConcertRoom;
    private String concertRoomName;
    private String address;
    private BigDecimal rentCosts;
    private List<Seat> seats;
}
