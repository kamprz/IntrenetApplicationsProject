package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.ConcertRoom;

public interface ConcertRoomRepository extends CrudRepository<ConcertRoom, Integer> {
}
