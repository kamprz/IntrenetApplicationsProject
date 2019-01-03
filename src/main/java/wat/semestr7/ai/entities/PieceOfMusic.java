package wat.semestr7.ai.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class PieceOfMusic
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPiece;
    private String titlePiece;
    private String composer;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "repertoire",
            joinColumns = @JoinColumn(name = "idPiece"),
            inverseJoinColumns = @JoinColumn(name = "idConcert")
    )
    private List<Concert> concerts;

    public PieceOfMusic(String titlePiece, String composer) {
        this.titlePiece = titlePiece;
        this.composer = composer;
    }

}
