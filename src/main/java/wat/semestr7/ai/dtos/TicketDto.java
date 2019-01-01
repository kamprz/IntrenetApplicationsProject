package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.entities.Purchase;
import wat.semestr7.ai.entities.Seat;

import javax.persistence.*;

@Getter
@Setter
@ToString
public class TicketDto
{
    private Integer seatRow;
    private Integer seatCol;
    private String discountName;
}
