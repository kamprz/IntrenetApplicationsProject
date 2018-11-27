package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.PieceOfMusic;

public interface PieceOfMusicRepository extends CrudRepository<PieceOfMusic,Integer> {
}
