package wat.semestr8.tim.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.entities.PieceOfMusic;

import java.util.List;

public interface PieceOfMusicRepository extends CrudRepository<PieceOfMusic,Integer>
{
    List<PieceOfMusic> findAll();

    PieceOfMusic findFirstByTitlePieceAndComposer(String name, String composer);
}
