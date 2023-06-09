package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.dto.RegisterDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.model.entities.Role;
import mk.ukim.finki.notefy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


//    public AppUser findByUsernameAndPassword(String username, String password) {
//        return userRepository.findByUsernameAndPassword(username, password)
//                .orElseThrow(() -> new BadRequest("User with those credentials doesn't exist"));
//    }

  public AppUser getByUsernameOrEmailOrNull(String username) {
    return userRepository.getByUsernameOrEmail(username, username)
        .orElse(null);
  }



    @PostConstruct
    public void init() {

        if (!userRepository.findByUsername("admin").isPresent()) {
            userRepository.save(
                    AppUser.builder()
                            .username("admin")
                            .firstName("Admin")
                            .lastName("Administrator")
                            .role(Role.ADMIN)
                            .password(passwordEncoder.encode("admin"))
                            .build()
            );
        }

    }

  public AppUser getByUsernameOrNull(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  public AppUser createUser(RegisterDto registerDto) {
    AppUser user = getByUsernameOrNull(registerDto.getUsername());
    if (user != null) {
      throw new BadRequest("User with username: " + registerDto.getUsername() + " already exists!");
    }
    user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
    if (user != null) {
      throw new BadRequest("User with email: " + registerDto.getEmail() + " already exists!");
    }

    return userRepository.save(
        AppUser.builder()
            .username(registerDto.getUsername())
            .password(passwordEncoder.encode(registerDto.getPassword()))
            .email(registerDto.getEmail())
            .firstName(registerDto.getFirstName())
            .lastName(registerDto.getLastName())
            .phoneNumber(registerDto.getPhoneNumber())
            .role(registerDto.getRole())
            .build()
    );
  }

  public AppUser getCurrentUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    return userRepository.getByUsernameOrEmail(username, username)
        .orElseThrow(() -> new BadRequest("You are not authenticated"));
  }

}