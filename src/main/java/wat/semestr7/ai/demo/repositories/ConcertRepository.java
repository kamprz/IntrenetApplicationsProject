package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Concert;

public interface ConcertRepository extends CrudRepository<Concert,Integer> {
}
