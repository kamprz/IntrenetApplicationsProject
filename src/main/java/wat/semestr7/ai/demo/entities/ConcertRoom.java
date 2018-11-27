package wat.semestr7.ai.demo.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class ConcertRoom
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idConcertRoom;
    private String concertRoomName;
    private BigDecimal rentCosts;
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "concertRoom",
            cascade = CascadeType.ALL)
    private List<Seat> seats;
}
