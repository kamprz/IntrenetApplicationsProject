package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wat.semestr7.ai.entities.Ticket;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
public class PurchaseDto
{
    private Integer concertId;
    private boolean isPaid;
    private String email;
    private List<TicketDto> tickets;
}
