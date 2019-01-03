package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Integer>
{
    /*@Query("select t from transaction order by date desc limit 1")
    Transaction findLastTransaction();*/
}
