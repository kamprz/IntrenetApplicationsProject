package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Budget;

public interface BudgetRepository extends CrudRepository<Budget,Integer> {
}
