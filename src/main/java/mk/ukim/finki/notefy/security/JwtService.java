package mk.ukim.finki.notefy.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mk.ukim.finki.notefy.model.entities.AppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtService {
    private Long expirationTime = 3_600_000_000_000L;

    private String keyStr = "Asdsadsaed21aweasdadxasd12dsad3!!!";
    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(keyStr.getBytes());
    }

    public String createToken(Authentication auth) {
        String username = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String strAuthorities = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return createToken(username, strAuthorities);

    }

    public String createToken(AppUser user) {
        return createToken(user.getUsername(), user.getRole());
    }
    String createToken(String username, String authorities) {
        Calendar cal = Calendar.getInstance();
        Date cd = cal.getTime(); // cd => current year
        cal.add(Calendar.YEAR, 1);
        Date ny = cal.getTime();	// ny => get

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(ny)
                .claim("AUTHORITIES_KEY", authorities)
                .signWith(secretKey)
                .compact();
    }
    public Authentication getAuthentication(String validToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(validToken).getBody();
        String principal = claims.getSubject();
        Object authoritiesClaim = claims.get("AUTHORITIES_KEY");

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        return new UsernamePasswordAuthenticationToken(principal, validToken, authorities);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
