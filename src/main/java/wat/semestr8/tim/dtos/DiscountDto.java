package wat.semestr8.tim.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto
{
    private int discountId;
    @NotBlank
    private String name;
    private int percents;
}
