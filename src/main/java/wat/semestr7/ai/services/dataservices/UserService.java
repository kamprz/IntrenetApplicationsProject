package wat.semestr7.ai.services.dataservices;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.AppUser;
import wat.semestr7.ai.repositories.UserRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;


@Service
public class UserService implements UserDetailsService
{
    private UserRepository applicationUserRepository;

    public UserService(UserRepository applicationUserRepository)
    {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        AppUser appUser = applicationUserRepository.findByEmail(email);
        if (appUser == null) throw new UsernameNotFoundException(email);
        List<GrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
        return new User(appUser.getEmail(), appUser.getPassword(), authorities);
    }

    public Collection<GrantedAuthority> getAuthorities(String email) throws UsernameNotFoundException
    {
        AppUser appUser = applicationUserRepository.findByEmail(email);
        if (appUser == null) throw new UsernameNotFoundException(email);
        List<GrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
        return authorities;
    }
}