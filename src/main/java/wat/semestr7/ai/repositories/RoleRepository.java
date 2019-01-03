package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.security.user.Role;

public interface RoleRepository extends CrudRepository<Role,String> {
}
