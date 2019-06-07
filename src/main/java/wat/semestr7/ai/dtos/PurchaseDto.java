package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wat.semestr7.ai.entities.Ticket;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class PurchaseDto
{
    @NotNull
    private Integer concertId;
    private boolean isPaid;
    @NotEmpty
    private String email;
    @NotEmpty
    private List<TicketDto> tickets;
}
