package wat.semestr8.tim.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Performers
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerformers;
    private String details;
    private BigDecimal costOfPersonnel;
}
