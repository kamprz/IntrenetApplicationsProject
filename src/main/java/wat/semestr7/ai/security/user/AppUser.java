package wat.semestr7.ai.security.user;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "appuser")
public class AppUser
{
    @Id
    private String email;
    private String password;
   // private String role;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleName")
    private Role role;
}
