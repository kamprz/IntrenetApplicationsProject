package wat.semestr7.ai.services.dataservices;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.User;
import wat.semestr7.ai.repositories.UserRepository;

import static java.util.Collections.emptyList;


@Service
class UserDetailsServiceImpl implements UserDetailsService
{
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository)
    {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        wat.semestr7.ai.entities.User applicationUser = applicationUserRepository.findByEmail(email);
        if (applicationUser == null) throw new UsernameNotFoundException(email);
        return new org.springframework.security.core.userdetails.User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
    }
}