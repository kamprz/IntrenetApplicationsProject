package wat.semestr7.ai.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.Transaction;
import wat.semestr7.ai.repositories.TransactionRepository;

import java.util.Optional;

@Service
public class TransactionService
{
    private TransactionRepository repo;

    public TransactionService(TransactionRepository repo)
    {
        this.repo=repo;
    }

    public void addBudget(Transaction transaction)
    {
        repo.save(transaction);
    }

    public Iterable<Transaction> getAllBudgets()
    {
        return repo.findAll();
    }

    public Optional<Transaction> getBudgetById(int id)
    {
        return repo.findById(id);
    }
}
