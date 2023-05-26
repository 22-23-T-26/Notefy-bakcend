package mk.ukim.finki.notefy.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.notefy.model.dto.AppUserDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

  private final UserService userService;

  @GetMapping("/{username}")
  public ResponseEntity<AppUserDto> getDetailsForUser(@PathVariable String username) {

    AppUser appUser = userService.getByUsernameOrNull(username);

    AppUserDto appUserDto = new AppUserDto(appUser.getFirstName(), appUser.getLastName(), appUser.getUsername(),
        userService.getRatingForUser(username), appUser.getEmail(), appUser.getPhoneNumber(), appUser.getPostsOfUsers());

    return ResponseEntity.ok(appUserDto);
  }
}
