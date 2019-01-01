package wat.semestr7.ai.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Concert
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConcert;
    private String concertTitle;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private BigDecimal additionalOrganisationCosts;
    private BigDecimal ticketCost;
    @Column(columnDefinition = "boolean default false")
    private boolean isApproved;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="idConcertRoom")
    private ConcertRoom concertRoom;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="idPerformers")
    private Performers concertPerformers;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "repertoire",
            joinColumns = @JoinColumn(name = "idConcert"),
            inverseJoinColumns = @JoinColumn(name = "idPiece")
    )
    private List<PieceOfMusic> repertoire = new LinkedList<>();

    public void addPieceOfMusic(PieceOfMusic piece)
    {
        if(!repertoire.contains(piece)) repertoire.add(piece);
    }

}
