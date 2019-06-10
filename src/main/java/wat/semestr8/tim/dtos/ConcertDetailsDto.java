package wat.semestr8.tim.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetailsDto {
    private int idConcert;
    private String concertTitle;
    private String date;
}
