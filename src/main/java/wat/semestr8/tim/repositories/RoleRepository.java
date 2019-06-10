package wat.semestr8.tim.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.security.user.Role;

public interface RoleRepository extends CrudRepository<Role,String> {
}
