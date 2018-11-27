package wat.semestr7.ai.demo.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.demo.entities.Budget;
import wat.semestr7.ai.demo.repositories.BudgetRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
public class BudgetService
{
    private BudgetRepository repo;
    //@Autowired
    public BudgetService(BudgetRepository repo)
    {
        this.repo=repo;
    }
    //@PostConstruct
    private void init()
    {
        for(int i=0;i<10;i++)
        {
            Budget b = new Budget();
            b.setAmountAfterTransaction(new BigDecimal("1.23" + i));
            b.setDetailsTransaction("details");
            b.setTitleTransaction("title " + i);
            b.setTransactionSum(new BigDecimal("1.23"));
            System.out.println("Saving: " + b);
            repo.save(b);
        }

        System.out.println("All:");
        repo.findAll().forEach(p -> System.out.println(p));
    }
}
