package wat.semestr7.ai.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class Role {
    @Id
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "roleName"),
            inverseJoinColumns = @JoinColumn(name = "authName"))
    private List<Authority> authorities;
}
