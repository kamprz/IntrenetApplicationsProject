package wat.semestr7.ai.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PieceOfMusicDto
{
    private int idPiece;
    private String title;
    private String composer;
}