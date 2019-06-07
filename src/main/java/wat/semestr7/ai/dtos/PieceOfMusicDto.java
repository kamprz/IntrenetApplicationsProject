package wat.semestr7.ai.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class PieceOfMusicDto
{
    private int idPiece;
    @NotEmpty
    private String title;
    @NotEmpty
    private String composer;
}