package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
}
