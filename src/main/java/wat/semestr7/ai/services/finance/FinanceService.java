package wat.semestr7.ai.services.finance;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.finance.MonthSummary;
import wat.semestr7.ai.dtos.finance.TransactionDto;
import wat.semestr7.ai.services.dataservices.ConcertService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceService {
    private ConcertService concertService;
    private TransactionService transactionService;

    public FinanceService(ConcertService concertService, TransactionService transactionService) {
        this.concertService = concertService;
        this.transactionService = transactionService;
    }

    public MonthSummary getMonthSummary(int month, int year) {
        MonthSummary result = new MonthSummary();
        Calendar startDay = Calendar.getInstance();
        startDay.set(year,month,1);

        Calendar endDay = Calendar.getInstance();
        endDay.set(Calendar.YEAR,startDay.get(Calendar.YEAR));
        endDay.set(Calendar.MONTH, startDay.get(Calendar.MONTH)+1);
        endDay.set(Calendar.DAY_OF_MONTH,1);

        List<TransactionDto> transactions = transactionService.getAllBudgets()
                .stream()
                .filter(t -> {  //is transaction in the month
                    Calendar transactionCal = Calendar.getInstance();
                    transactionCal.setTime(t.getDate());
                    if (transactionCal.compareTo(startDay) < 0) return false;
                    else if (transactionCal.compareTo(endDay) > 0) return false;
                    else return true;
                }).sorted(Comparator.comparing(TransactionDto::getDate))
                .collect(Collectors.toList());
        result.setTransactions(transactions);

        BigDecimal totalExpenses = new BigDecimal("0.0");
        BigDecimal totalEarning = new BigDecimal("0.0");
        BigDecimal balance = new BigDecimal("0.0");
        for(TransactionDto transaction : transactions)
        {
            balance = balance.add(transaction.getTransactionSum());
            if(transaction.getTransactionSum().doubleValue() > 0) totalEarning = totalEarning.add(transaction.getTransactionSum());
            if(transaction.getTransactionSum().doubleValue() < 0) totalExpenses = totalExpenses.add(transaction.getTransactionSum());
        }
        balance.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        totalExpenses.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        totalEarning.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        result.setBalance(balance);
        result.setTotalEarning(totalEarning);
        result.setTotalExpenses(totalExpenses);
        return result;
    }
}
