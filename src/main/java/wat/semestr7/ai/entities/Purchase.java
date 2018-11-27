package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Purchase
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPurchase;
    private String emailAddress;
    @Column(name = "isPurchasePaid", columnDefinition = "boolean default false", nullable = false)
    private boolean isPurchasePaid;
    @OneToMany(mappedBy = "purchase",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Ticket> tickets;
}
