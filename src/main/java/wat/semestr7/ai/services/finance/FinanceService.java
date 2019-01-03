package wat.semestr7.ai.services.finance;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.services.dataservices.ConcertService;
import wat.semestr7.ai.services.dataservices.TransactionService;

@Service
public class FinanceService {
    private ConcertService concertService;
    private TransactionService transactionService;

    public FinanceService(ConcertService concertService, TransactionService transactionService) {
        this.concertService = concertService;
        this.transactionService = transactionService;
    }
}
