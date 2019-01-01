package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.services.dataservices.PieceOfMusicService;

@RestController
public class PieceOfMusicController
{
    private PieceOfMusicService pieceOfMusicService;

    public PieceOfMusicController(PieceOfMusicService pieceOfMusicService) {
        this.pieceOfMusicService = pieceOfMusicService;
    }
}
