package wat.semestr8.tim.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TicketDto
{
    private Integer seatRow;
    private Integer seatCol;
    private String discountName;
}
