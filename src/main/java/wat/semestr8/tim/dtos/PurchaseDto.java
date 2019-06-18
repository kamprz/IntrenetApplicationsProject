package wat.semestr8.tim.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto
{
    @NotNull
    private Integer concertId;
    private boolean isPaid;
    private String email;
    @NotEmpty
    private List<TicketDto> tickets;
    private String userId;
}
