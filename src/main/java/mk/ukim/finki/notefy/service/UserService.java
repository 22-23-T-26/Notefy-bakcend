package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.dto.RegisterDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AppUser findByUsernameOrEmailAndPassword(String username, String email, String password) {
        return userRepository.findByUsernameOrEmailAndPassword(username, email, password)
                .orElseThrow(() -> new BadRequest("User with those credentials doesn't exist"));
    }

    public AppUser createUser(RegisterDto registerDto) {
        AppUser user = userRepository.findByUsername(registerDto.getUsername()).orElse(null);
        if (user != null) {
            throw new BadRequest("User with username: " +registerDto.getUsername() + " already exists!");
        }
        user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        if (user != null) {
            throw new BadRequest("User with email: " +registerDto.getEmail() + " already exists!");
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
    
    //TODO: Update user
}