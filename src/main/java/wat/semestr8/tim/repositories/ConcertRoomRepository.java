package wat.semestr8.tim.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.entities.ConcertRoom;

public interface ConcertRoomRepository extends CrudRepository<ConcertRoom, Integer>
{
    public ConcertRoom findFirstByConcertRoomName(String name);

    /*@Query("SELECT count(all) from (select distinct row FROM seat) as t")
    Integer countRows();*/

}
