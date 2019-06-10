package wat.semestr8.tim.dtos.finance;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
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
    private String accountBalanceAtTheEndOfMonth;
    private List<TransactionDto> transactions;
}
