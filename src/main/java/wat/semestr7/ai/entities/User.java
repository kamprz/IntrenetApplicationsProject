package wat.semestr7.ai.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "`user`")
public class User
{
    @Id
    private String email;
    private String password;
    private String role;
    private String token;
}
