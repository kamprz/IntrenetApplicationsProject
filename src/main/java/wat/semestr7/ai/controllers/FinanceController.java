package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.ConcertDetailsDto;
import wat.semestr7.ai.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr7.ai.dtos.finance.MonthSummaryDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.exceptions.customexceptions.WrongRequestParameterException;
import wat.semestr7.ai.services.finance.FinanceService;

import java.util.List;

@RestController
public class FinanceController
{
    private FinanceService finanseService;

    public FinanceController(FinanceService finanseService) {
        this.finanseService = finanseService;
    }

    @GetMapping(value = "/admin/finance/month-summary")
    public ResponseEntity<MonthSummaryDto> getMonthSummary(@RequestParam int month, @RequestParam int year) throws WrongRequestParameterException {
        if(month<1 || month > 12) throw new WrongRequestParameterException("Wrong month value. Must be integer between 1 and 12");
        if(year < 2000 & year > 3000) throw new WrongRequestParameterException("Wrong year value. Must be integer between 2000 and 3000");
        return ResponseEntity.ok().body(finanseService.getMonthSummary(month,year));
    }

    @GetMapping("admin/finance/summary-list")
    public ResponseEntity<List<ConcertDetailsDto>> getConcertDetailsList()
    {
        return ResponseEntity.ok().body(finanseService.getConcertDetailDtoList());
    }

    @GetMapping(value = "/admin/finance/concert/{id}")
    public ResponseEntity<ConcertFinanceSummaryDto> getConcertFinanceSummary(@PathVariable("id") int id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(finanseService.getConcertSummary(id));
    }
}
