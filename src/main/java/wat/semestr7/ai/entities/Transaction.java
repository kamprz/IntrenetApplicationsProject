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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransaction;
    private BigDecimal transactionSum;
    private BigDecimal amountAfterTransaction;
    private String titleTransaction;
    private String transactionDetails;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
