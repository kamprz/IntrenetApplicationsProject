package wat.semestr7.ai.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.entities.Transaction;
import wat.semestr7.ai.services.dataservices.TransactionService;

@RestController
public class TransactionController
{
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("budget")
    public void addBudget(@RequestBody Transaction transaction)
    {
        transactionService.addBudget(transaction);
    }

    @GetMapping("budget")
    public ResponseEntity<Iterable<Transaction>> getAllBudgets()
    {
        return new ResponseEntity<>(transactionService.getAllBudgets(), HttpStatus.OK);
    }

    @GetMapping("budget/{id}")
    public ResponseEntity<Transaction> getSingleBudget(@PathVariable("id") int id)
    {
        Transaction transaction = transactionService.getBudgetById(id).orElse(null);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }
}
