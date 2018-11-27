package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Seat;

public interface SeatRepository extends CrudRepository<Seat,Integer> {
}
