package wat.semestr8.tim.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
