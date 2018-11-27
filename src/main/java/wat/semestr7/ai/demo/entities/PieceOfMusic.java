package wat.semestr7.ai.demo.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class PieceOfMusic
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPiece;
    private String titlePiece;
    private String composer;
    private int durationInMinutes;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "repertoire",
            joinColumns = @JoinColumn(name = "idPiece"),
            inverseJoinColumns = @JoinColumn(name = "idConcert")
    )
    private List<Concert> concerts;
}
