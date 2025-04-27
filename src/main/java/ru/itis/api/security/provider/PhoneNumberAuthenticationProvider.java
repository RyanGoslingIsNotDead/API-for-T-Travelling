package ru.itis.api.security.provider;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.api.security.token.PhoneNumberPasswordAuthenticationToken;

@Component
@RequiredArgsConstructor
public class PhoneNumberAuthenticationProvider implements AuthenticationProvider {


    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(phoneNumber);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid phone number or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid phone number or password");
        }

        return new PhoneNumberPasswordAuthenticationToken(
                phoneNumber,
                password,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneNumberPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
