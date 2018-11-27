package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Performers;

public interface PerformersRepository extends CrudRepository<Performers, Integer> {
}
