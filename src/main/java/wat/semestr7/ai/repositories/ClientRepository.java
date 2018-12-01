package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Client;

public interface ClientRepository extends CrudRepository<Client, String>
{

}
