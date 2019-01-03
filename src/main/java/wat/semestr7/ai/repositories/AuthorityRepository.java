package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.security.user.Authority;

public interface AuthorityRepository extends CrudRepository<Authority,String>{
}
