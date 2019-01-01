package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "`appuser`")
public class AppUser
{
    @Id
    private String email;
    private String password;
    private String role;
    private String token;
}
