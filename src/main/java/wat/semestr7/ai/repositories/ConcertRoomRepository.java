package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.entities.ConcertRoom;

import java.util.List;

public interface ConcertRoomRepository extends CrudRepository<ConcertRoom, Integer>
{

}
