package wat.semestr8.tim.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.security.user.Authority;

public interface AuthorityRepository extends CrudRepository<Authority,String>{
}
