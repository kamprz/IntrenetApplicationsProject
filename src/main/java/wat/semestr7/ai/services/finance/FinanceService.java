package wat.semestr7.ai.services.finance;

import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.ConcertDetailsDto;
import wat.semestr7.ai.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr7.ai.dtos.finance.MonthSummaryDto;
import wat.semestr7.ai.dtos.finance.TransactionDto;
import wat.semestr7.ai.dtos.mappers.ConcertMapper;
import wat.semestr7.ai.dtos.mappers.EntityToDtoMapper;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.entities.Ticket;
import wat.semestr7.ai.entities.Transaction;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.dataservices.ConcertService;
import wat.semestr7.ai.services.dataservices.TicketService;
import wat.semestr7.ai.utils.DateUtils;
import wat.semestr7.ai.utils.PriceUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceService {
    private ConcertService concertService;
    private TransactionService transactionService;
    private TicketService ticketService;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public FinanceService(ConcertService concertService, TransactionService transactionService, TicketService ticketService) {
        this.concertService = concertService;
        this.transactionService = transactionService;
        this.ticketService = ticketService;
    }

    public MonthSummaryDto getMonthSummary(int month, int year) {
        MonthSummaryDto result = new MonthSummaryDto();
        Calendar startDay = Calendar.getInstance();
        startDay.set(year,month,1,0,0,1);
        startDay.set(Calendar.MONTH,startDay.get(Calendar.MONTH) -1);
        //dziwne to ale działa...tak jakby źle przyjmowało miesiąc (o jeden za późny, stąd odejmowanie)

        Calendar endDay = Calendar.getInstance();
        endDay.set(year,month,1,0,0,0);
        endDay.set(Calendar.SECOND,-1);

        result.setBeginDate(DateUtils.formatDate(startDay.getTime()));
        result.setEndDate(DateUtils.formatDate(endDay.getTime()));

        List<TransactionDto> transactions = transactionService.getAllBudgets().stream()
                .filter(t -> {  //is transaction in the month
                    Calendar transactionCal = Calendar.getInstance();
                    transactionCal.setTime(t.getDate());
                    if (transactionCal.compareTo(startDay) <= 0) return false;
                    else if (transactionCal.compareTo(endDay) >= 0) return false;
                    else return true;
                }).sorted(Comparator.comparing(Transaction::getDate).reversed())
                .map(t -> mapper.transactionToDto(t))
                .collect(Collectors.toList());

        result.setTransactions(transactions);

        BigDecimal totalExpenses = new BigDecimal("0.0");
        BigDecimal totalEarning = new BigDecimal("0.0");
        BigDecimal balance = new BigDecimal("0.0");
        String accountCurrentBalance;
        if(transactions!= null && transactions.size()>0)
            accountCurrentBalance = transactions.get(0).getAmountAfterTransaction().setScale(2,BigDecimal.ROUND_UNNECESSARY).toString();
        else accountCurrentBalance = "Brak danych ze wskazanego miesiąca";
        result.setAccountBalanceAtTheEndOfMonth(accountCurrentBalance);
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
        result.setTotalExpenses(totalExpenses.multiply(new BigDecimal("-1")));
        return result;
    }

    public ConcertFinanceSummaryDto getConcertSummary(int id) throws EntityNotFoundException {
        Concert concert = concertService.getConcert(id);
        ConcertFinanceSummaryDto financeDto = ConcertMapper.concertToFinanceSummarySimpleFieldsMapping(concert);

        List<Ticket> allTicketsSold = ticketService.getAllTicketsByConcert(concert);
        financeDto.setAmountOfTicketsSold(allTicketsSold.size());

        BigDecimal ticketIncome = new BigDecimal("0.0");
        ticketIncome.setScale(2,BigDecimal.ROUND_UNNECESSARY);
        for(Ticket t : allTicketsSold)
        {
            ticketIncome = ticketIncome.add(PriceUtils.getTicketPrice(concert.getTicketCost(),t.getDiscount().getPercents()));
        }
        financeDto.setIncomeFromTickets(ticketIncome);
        return financeDto;
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void financialSummaryOfEndedConcerts()
    {
        List<Concert> concertsToSummarize = concertService.getAllConcerts()
                .stream()
                .filter(c -> !c.isWasSummarized())
                .filter(c -> c.getDate().compareTo(new Date()) > 0)
                .collect(Collectors.toList());
        for(Concert concert : concertsToSummarize)
        {
            BigDecimal concertCost = concert.getAdditionalOrganisationCosts()
                    .add(concert.getConcertPerformers().getCostOfPersonnel())
                    .add(concert.getConcertRoom().getRentCosts());

            Transaction transaction = new Transaction();
            transaction.setTransactionDetails("Koncert : " + concert.getConcertTitle() + ". Data : " + concert.getDate());
            transaction.setTitleTransaction("Opłaty za organizację koncertu");
            transaction.setDate(new Date());
            transaction.setTransactionSum(concertCost.multiply(new BigDecimal("-1")));

            transaction.setAmountAfterTransaction(
                    new BigDecimal(transactionService.getCurrentCash())
                    .add(transaction.getTransactionSum()));
            transactionService.saveTransaction(transaction);
            concert.setWasSummarized(true);
            concertService.saveConcert(concert);
        }
    }

    public List<ConcertDetailsDto> getConcertDetailDtoList()
    {
        return concertService.getConcertDetailDtoList();
    }
}
