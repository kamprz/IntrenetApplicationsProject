package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer>
{
    Purchase findFirstByPaypalID(String paypalID);
}
