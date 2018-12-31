package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Performers;

public interface PerformersService extends CrudRepository<Performers, Integer> {
}
