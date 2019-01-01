package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.entities.PieceOfMusic;

import java.util.List;

public interface PieceOfMusicRepository extends CrudRepository<PieceOfMusic,Integer>
{
    @Query(value = "select new wat.semestr7.ai.entities.PieceOfMusic(r.titlePiece,r.composer)" +
            " from Concert c join c.repertoire r" +
            " where c.idConcert = :id")
    List<PieceOfMusic> findConcertRepertoire(@Param("id") int id);
}
