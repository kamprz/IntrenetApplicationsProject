package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Sponsor;

public interface SponsorRepository extends CrudRepository<Sponsor,Integer> {
}
