package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.PieceOfMusicRepository;

@Service
public class PieceOfMusicService
{
    private PieceOfMusicRepository pieceOfMusicRepository;

    public PieceOfMusicService(PieceOfMusicRepository pieceOfMusicRepository) {
        this.pieceOfMusicRepository = pieceOfMusicRepository;
    }
}
