package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Sponsor;

public interface SponsorRepository extends CrudRepository<Sponsor,Integer> {
}
