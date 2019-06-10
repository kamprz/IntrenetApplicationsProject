package wat.semestr7.ai.dtos.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionConcertDetails {
    private String concertTitle;
    private Date date;
}