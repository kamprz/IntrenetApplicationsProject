package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Performers;

import java.util.List;

public interface PerformersRepository extends CrudRepository<Performers, Integer>
{
    Performers findFirstByDetails(String details);

    List<Performers> findAll();
}
