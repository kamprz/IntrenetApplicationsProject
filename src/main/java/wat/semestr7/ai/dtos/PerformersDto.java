package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
public class PerformersDto
{
    private int idPerformers;
    @NotEmpty
    private String details;
    private BigDecimal costOfPersonnel;
}
