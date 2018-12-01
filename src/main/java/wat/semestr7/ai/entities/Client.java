package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Client
{
    @Id
    private String login;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Purchase> purchases;
}
