package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.ConcertRoom;

public interface ConcertRoomRepository extends CrudRepository<ConcertRoom, Integer> {
}
