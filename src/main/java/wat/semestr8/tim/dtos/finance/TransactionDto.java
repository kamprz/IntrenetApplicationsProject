package wat.semestr8.tim.dtos.finance;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDto
{
    private BigDecimal transactionSum;
    private BigDecimal amountAfterTransaction;
    private String titleTransaction;
    private String transactionDetails;
    private String date;
}
