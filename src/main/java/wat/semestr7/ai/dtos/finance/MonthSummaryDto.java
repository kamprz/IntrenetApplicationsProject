package wat.semestr7.ai.dtos.finance;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wat.semestr7.ai.entities.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class MonthSummaryDto {
    private String beginDate;
    private String endDate;
    private BigDecimal totalExpenses;
    private BigDecimal totalEarning;
    private BigDecimal balance;
    private List<TransactionDto> transactions;
}
