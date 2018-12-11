package wat.semestr7.ai.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
public class Seat
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSeat;
    private int row;
    private int position;
    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="idConcertRoom")
    private ConcertRoom concertRoom;
}
