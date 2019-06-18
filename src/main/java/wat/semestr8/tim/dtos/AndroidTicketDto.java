package wat.semestr8.tim.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AndroidTicketDto {
    private String concertTitle;
    private String date;
    private Double cost;
    private String discount;
    private String concertRoom;
    private int row;
    private int position;
}
