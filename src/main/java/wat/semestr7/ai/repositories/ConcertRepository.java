package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.dtos.ConcertDTO;
import wat.semestr7.ai.dtos.ConcertDetailedDTO;
import wat.semestr7.ai.dtos.PieceOfMusicDTO;
import wat.semestr7.ai.entities.Concert;

import java.util.List;

public interface ConcertRepository extends CrudRepository<Concert,Integer>
{

    @Query(value="Select new wat.semestr7.ai.dtos.ConcertDetailedDTO(c.idConcert,c.concertTitle,c.date,cr.concertRoomName,cr.adress,cp.details,c.ticketCost)" +
            " from Concert c" +
            " left join c.concertPerformers cp" +
            " left join c.concertRoom cr" +
            " where c.idConcert = :id")
    ConcertDetailedDTO findByIdConcertWithDetails(@Param("id") int id);

    @Query(value = "select new wat.semestr7.ai.dtos.PieceOfMusicDTO(r.titlePiece,r.composer)" +
            " from Concert c join c.repertoire r" +
            " where c.idConcert = :id")
    List<PieceOfMusicDTO> findConcertRepertoire(@Param("id") int id);

    @Query(value="Select new wat.semestr7.ai.dtos.ConcertDTO(c.idConcert,c.concertTitle,c.date,cr.concertRoomName)" +
            " from Concert c" +
            " left join c.concertRoom cr")
    List<ConcertDTO> findAllConcerts();


}
