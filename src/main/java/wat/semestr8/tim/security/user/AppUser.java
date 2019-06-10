package wat.semestr8.tim.security.user;

import lombok.Data;

import javax.persistence.*;

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
