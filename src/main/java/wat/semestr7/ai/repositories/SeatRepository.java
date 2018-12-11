package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.dtos.FreeSeatDTO;
import wat.semestr7.ai.entities.Seat;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat,Integer>
{
    Seat findFirstByRowAndPosition(int row, int position);

    @Query(value="Select new wat.semestr7.ai.dtos.FreeSeatDTO(se.row,se.position) from Seat se" +
            " where idSeat not in" +
            " (select s.idSeat from Ticket t" +
            " left join t.concert c" +
            " left join t.seat s" +
            " where c.idConcert = :concertId" +
            ")")
    List<FreeSeatDTO> getAllFreeSeatsOnConcert(@Param("concertId") int concertId);

    /*
    Select se.id_seat from "seat" se
    where id_seat not in
    (
     select s.id_seat from "ticket" t
     join "concert" c on c.id_concert=t.id_concert
     join "seat" s on s.id_seat = t.id_seat
    )
     */

}
