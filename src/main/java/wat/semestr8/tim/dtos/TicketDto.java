package wat.semestr8.tim.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketDto
{
    private Integer seatRow;
    private Integer seatCol;
    private String discountName;
}
