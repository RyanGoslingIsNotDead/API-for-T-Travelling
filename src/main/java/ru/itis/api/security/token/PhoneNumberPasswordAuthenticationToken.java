package ru.itis.api.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PhoneNumberPasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final String phoneNumber;
    private final String password;

    public PhoneNumberPasswordAuthenticationToken(String phoneNumber, String password) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.password = password;
        setAuthenticated(false);
    }

    public PhoneNumberPasswordAuthenticationToken(String phoneNumber, String password,
                                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phoneNumber = phoneNumber;
        this.password = password;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return phoneNumber;
    }
}
