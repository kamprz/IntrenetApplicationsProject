package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.AppUser;

public interface UserRepository extends CrudRepository<AppUser, String>
{
    public AppUser findByEmail(String email);
}
