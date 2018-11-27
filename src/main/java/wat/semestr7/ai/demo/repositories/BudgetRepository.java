package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Budget;

public interface BudgetRepository extends CrudRepository<Budget,Integer> {
}
