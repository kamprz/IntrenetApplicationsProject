package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TransactionDto
{
    private int idTransaction;
    private BigDecimal transactionSum;
    private BigDecimal amountAfterTransaction;
    private String titleTransaction;
    private String detailsTransaction;
    private Date date;
}
