package wat.semestr7.ai.services.finance;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.finance.TransactionDto;
import wat.semestr7.ai.dtos.mappers.EntityToDtoMapper;
import wat.semestr7.ai.entities.Purchase;
import wat.semestr7.ai.entities.Transaction;
import wat.semestr7.ai.repositories.TransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService
{
    private TransactionRepository repo;
    private EntityToDtoMapper mappper = Mappers.getMapper(EntityToDtoMapper.class);

    public TransactionService(TransactionRepository repo)
    {
        this.repo=repo;
    }

    public void addTransaction(Purchase purchase)
    {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        //transaction.setDetailsTransaction();
       // transaction.setTitleTransaction("Opłata za bilety na koncert : " + repo.getConcertTitleByPurchaseId(purchase.getIdPurchase()));
      //  transaction.setTransactionSum(repo.getPurchaseCost(purchase.getIdPurchase()));
        //transaction.setAmountAfterTransaction();
        repo.save(transaction);
    }

    public List<TransactionDto> getAllBudgets()
    {
        return repo.findAll().stream().map(t -> mappper.transactionToDto(t)).collect(Collectors.toList());
    }

    public Optional<Transaction> getBudgetById(int id)
    {
        return repo.findById(id);
    }
}
