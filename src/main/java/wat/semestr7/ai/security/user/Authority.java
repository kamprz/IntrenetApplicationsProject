package wat.semestr7.ai.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Authority {
    @Id
    private String authName;

    public Authority(String authName) {
        this.authName = authName;
    }

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    List<Role> roles;
}
