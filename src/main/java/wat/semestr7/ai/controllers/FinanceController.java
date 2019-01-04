package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.finance.MonthSummary;
import wat.semestr7.ai.exceptions.customexceptions.WrongRequestParameterException;
import wat.semestr7.ai.services.finance.FinanceService;

@RestController
public class FinanceController
{
    private FinanceService finanseService;

    public FinanceController(FinanceService finanseService) {
        this.finanseService = finanseService;
    }

    @GetMapping(value = "/admin/finance/month-summary")
    public ResponseEntity<MonthSummary> getMonthSummary(@RequestParam int month, @RequestParam int year) throws WrongRequestParameterException {
        if(month<1 || month > 12) throw new WrongRequestParameterException("Wrong month value. Must be integer between 1 and 12");
        if(year < 2000 & year > 3000) throw new WrongRequestParameterException("Wrong year value. Must be integer between 2000 and 3000");
        return ResponseEntity.ok().body(finanseService.getMonthSummary(month,year));
    }
}
