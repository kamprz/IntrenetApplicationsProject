package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Budget
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBudget;
    private BigDecimal transactionSum;
    private BigDecimal amountAfterTransaction;
    private String titleTransaction;
    private String detailsTransaction;
}
