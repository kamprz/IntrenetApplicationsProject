package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Sponsor
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSponsor;
    private String organisation;
}
