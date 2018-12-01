package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.entities.Budget;
import wat.semestr7.ai.services.BudgetService;

@RestController
public class BudgetController
{
    private BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }
}
