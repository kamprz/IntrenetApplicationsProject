package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer>
{
}
