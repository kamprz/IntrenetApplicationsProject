package wat.semestr8.tim.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.entities.Purchase;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase,Integer>
{
    Purchase findFirstByPaypalID(String paypalID);

    @Query(value = "select p from Purchase p where isPaid=false")
    List<Purchase> findAllWhereIsNotPaid();

    List<Purchase> findAllByUserId(String userId);
}
