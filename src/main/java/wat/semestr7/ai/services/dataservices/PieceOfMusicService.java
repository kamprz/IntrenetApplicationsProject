package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.PieceOfMusic;
import wat.semestr7.ai.repositories.PieceOfMusicRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class PieceOfMusicService
{
    private PieceOfMusicRepository pieceOfMusicRepository;

    public PieceOfMusicService(PieceOfMusicRepository pieceOfMusicRepository) {
        this.pieceOfMusicRepository = pieceOfMusicRepository;
    }
}
