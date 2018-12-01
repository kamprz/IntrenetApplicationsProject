package wat.semestr7.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PieceOfMusicDTO
{
    private String titlePiece;
    private String composer;

    public PieceOfMusicDTO(String titlePiece, String composer) {
        this.titlePiece = titlePiece;
        this.composer = composer;
    }
}
