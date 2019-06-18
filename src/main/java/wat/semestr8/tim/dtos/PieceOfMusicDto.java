package wat.semestr8.tim.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceOfMusicDto
{
    private int idPiece;
    @NotEmpty
    private String title;
    @NotEmpty
    private String composer;
}