package wat.semestr7.ai.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConcertRoom;
    private String concertRoomName;
    private String address;
    private BigDecimal rentCosts;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "concertRoom",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Seat> seats;
}
