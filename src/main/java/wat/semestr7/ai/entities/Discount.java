package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Discount
{
    @Id
    private String name;
    private int percents;
}
