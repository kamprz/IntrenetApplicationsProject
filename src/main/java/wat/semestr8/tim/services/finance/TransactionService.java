package wat.semestr8.tim.services.finance;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.finance.TransactionConcertDetails;
import wat.semestr8.tim.dtos.mappers.EntityToDtoMapper;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.entities.Transaction;
import wat.semestr8.tim.repositories.TransactionRepository;
import wat.semestr8.tim.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService
{
    private TransactionRepository repo;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public TransactionService(TransactionRepository repo)
    {
        this.repo=repo;
    }

    public void addTransaction(Purchase purchase)
    {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setTitleTransaction("Opłata za bilety na koncert.");
        TransactionConcertDetails details = repo.getTransactionConcertDetailsByPurchaseId(purchase.getIdPurchase());
        transaction.setTransactionDetails("Tytuł koncertu: " + details.getConcertTitle() +". Data koncertu: " + DateUtils.formatDate(details.getDate()));
        transaction.setTransactionSum( new BigDecimal(repo.getPurchaseCost(purchase.getIdPurchase())));
        Double currentBudget = repo.getCurrentCash();
        if(currentBudget == null) currentBudget = 0.0;
        System.out.println(currentBudget);
        transaction.setAmountAfterTransaction(new BigDecimal(currentBudget).add(transaction.getTransactionSum()));
        repo.save(transaction);
    }

    public List<Transaction> getAllBudgets()
    {
        return repo.findAll();
    }

    public Optional<Transaction> getBudgetById(int id)
    {
        return repo.findById(id);
    }

    public Double getCurrentCash()
    {
        return repo.getCurrentCash();
    }

    public void saveTransaction(Transaction t)
    {
        repo.save(t);
    }
}
