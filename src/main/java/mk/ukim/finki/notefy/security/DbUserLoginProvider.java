package mk.ukim.finki.notefy.security;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbUserLoginProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        AppUser user = userService.getByUsernameOrEmailOrNull(username);

        if (user == null) {
            throw new BadRequest("User with those credentials doesn't exist");
        }

        if (passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
//        return null;
        throw new BadRequest("User with those credentials doesn't exist");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
