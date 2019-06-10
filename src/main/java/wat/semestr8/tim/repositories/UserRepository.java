package wat.semestr8.tim.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.security.user.AppUser;

public interface UserRepository extends CrudRepository<AppUser, String>
{
    public AppUser findByEmail(String email);
}
