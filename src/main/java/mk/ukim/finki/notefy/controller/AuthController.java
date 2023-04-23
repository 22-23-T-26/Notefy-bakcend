package mk.ukim.finki.notefy.controller;

import mk.ukim.finki.notefy.model.dto.LoginDto;
import mk.ukim.finki.notefy.model.dto.RegisterDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.security.JwtService;
import mk.ukim.finki.notefy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping({"/login"})
    public String getToken(@Valid @RequestBody LoginDto loginDto) {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword()));

        return jwtService.createToken(auth);
    }


    @PostMapping({"/register"})
    public String register(@Valid @RequestBody RegisterDto registerDto) {
        AppUser user = userService.createUser(registerDto);

        return jwtService.createToken(user);
    }
}
