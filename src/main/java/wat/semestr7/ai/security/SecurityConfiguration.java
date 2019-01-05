package wat.semestr7.ai.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CorsFilter;
import wat.semestr7.ai.security.filter.JWTAuthenticationFilter;
import wat.semestr7.ai.security.filter.JWTAuthorizationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String SEED = "aSDksdnKJASDlasdaLDSkas";
    public static final String TOKEN_PREFIX = "Token ";
    public static final String AUTHORIZATION_HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

    private UserService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/demo").permitAll()
                .antMatchers("/free-seat/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/paypal/**").permitAll()
                .antMatchers("/discount").permitAll()
                .antMatchers(HttpMethod.GET,"/concert/approved").permitAll()
                .antMatchers("/admin/**").hasAuthority(SecurityAuthorities.ADMIN)
                .antMatchers(HttpMethod.GET,"/concert/not-approved").hasAuthority(SecurityAuthorities.READ_NOT_APPROVED)
                .antMatchers(HttpMethod.POST,"/concert/approve").hasAuthority(SecurityAuthorities.APPROVE)
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userDetailsService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("Authorization");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}