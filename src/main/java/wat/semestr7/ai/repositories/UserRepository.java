package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.User;

public interface UserRepository extends CrudRepository<User, String>
{

}
