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
    @Column(name = "isPaid", columnDefinition = "boolean default false", nullable = false)
    private boolean isPaid;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "login")
    private Client client;
}
