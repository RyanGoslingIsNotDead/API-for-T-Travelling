package ru.itis.api.security.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.api.security.details.UserDetailsImpl;
import ru.itis.api.security.details.UserDetailsServiceImpl;
import ru.itis.api.security.token.JwtAuthenticationToken;
import ru.itis.api.util.JwtUtil;


@Component
@RequiredArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String rawToken = (String) authentication.getCredentials();

        String phoneNumber = getPhoneNumber(rawToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(phoneNumber);

        return new JwtAuthenticationToken(userDetails);
    }

    private String getPhoneNumber(String rawToken) {
        try {
            return jwtUtil.getPhoneNumber(rawToken);
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Invalid token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
