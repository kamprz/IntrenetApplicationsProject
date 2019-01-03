package wat.semestr7.ai.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import wat.semestr7.ai.security.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static wat.semestr7.ai.security.SecurityConfiguration.AUTHORIZATION_HEADER_STRING;
import static wat.semestr7.ai.security.SecurityConfiguration.SEED;
import static wat.semestr7.ai.security.SecurityConfiguration.TOKEN_PREFIX;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
    private UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException
    {
        String header = request.getHeader(AUTHORIZATION_HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX))
        {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(AUTHORIZATION_HEADER_STRING);
        if (token != null)
        {
            String user = JWT.require(Algorithm.HMAC512(SEED.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null) return new UsernamePasswordAuthenticationToken(user, null, userService.getAuthorities(user));
            return null;
        }
        return null;
    }
}