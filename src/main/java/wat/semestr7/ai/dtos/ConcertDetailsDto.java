package wat.semestr7.ai.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetailsDto {
    private int idConcert;
    private String concertTitle;
    private Date date;
}
