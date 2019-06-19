package wat.semestr8.tim.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConcertDetailsWithDate {
    private int idConcert;
    private String concertTitle;
    private Date date;
}
