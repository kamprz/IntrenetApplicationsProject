package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTransaction;
    private BigDecimal transactionSum;
    private BigDecimal amountAfterTransaction;
    private String titleTransaction;
    private String detailsTransaction;
    @Temporal(TemporalType.DATE)
    private Date date;
}
