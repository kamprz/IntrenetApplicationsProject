package wat.semestr7.ai.others;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ConcertDetails
{
    private int idConcert;
    private String concertTitle;
    private Date date;
}
