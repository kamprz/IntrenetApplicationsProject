package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Seat;

public interface SeatRepository extends CrudRepository<Seat,Integer> {
}
