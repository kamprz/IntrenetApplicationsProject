package wat.semestr8.tim.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr8.tim.dtos.ConcertDetailsDto;
import wat.semestr8.tim.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr8.tim.dtos.finance.MonthSummaryDto;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.services.finance.FinanceService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class FinanceController
{
    private FinanceService finanseService;

    public FinanceController(FinanceService finanseService) {
        this.finanseService = finanseService;
    }

    @GetMapping(value = "/admin/finance/month-summary")
    public ResponseEntity<MonthSummaryDto> getMonthSummary(@RequestParam @Min(1) @Max(12) int month,
                                                           @RequestParam @Min(2000) @Max(3000) int year){
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
