package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Concert;

public interface ConcertRepository extends CrudRepository<Concert,Integer> {
}
