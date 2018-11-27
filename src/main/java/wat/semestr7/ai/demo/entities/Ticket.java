package wat.semestr7.ai.demo.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;
    private BigDecimal price;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="idConcert")
    private Concert concert;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="idSeat")
    private Seat seat;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="idPurchase")
    private Purchase purchase;
}
