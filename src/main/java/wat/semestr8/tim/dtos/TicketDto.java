package wat.semestr8.tim.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto
{
    private Integer seatRow;
    private Integer seatCol;
    private String discountName;
}
