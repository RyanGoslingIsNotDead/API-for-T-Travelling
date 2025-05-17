package ru.itis.api.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.api.security.details.UserDetailsImpl;

import java.util.Collection;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetailsImpl userDetails;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    public JwtAuthenticationToken(UserDetailsImpl userDetails) {
        super(null);
        this.userDetails = userDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

}
