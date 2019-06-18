package wat.semestr8.tim.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PerformersDto
{
    private int idPerformers;
    @NotEmpty
    private String details;
    @NotNull
    private BigDecimal costOfPersonnel;
}
