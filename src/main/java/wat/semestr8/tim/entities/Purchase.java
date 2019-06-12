package wat.semestr8.tim.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Purchase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPurchase;
    @Column(name = "isPaid", columnDefinition = "boolean default false", nullable = false)
    private boolean isPaid;
    private String email;
    @Column(name = "paypal_id")
    private String paypalID;
    private Date timestamp;
    private String userId;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
