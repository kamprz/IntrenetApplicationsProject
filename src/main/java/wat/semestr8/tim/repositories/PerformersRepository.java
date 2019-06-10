package wat.semestr8.tim.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.entities.Performers;

import java.util.List;

public interface PerformersRepository extends CrudRepository<Performers, Integer>
{
    Performers findFirstByDetails(String details);

    List<Performers> findAll();
}
