package mk.ukim.finki.notefy.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.notefy.model.dto.AppUserDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.service.PostService;
import mk.ukim.finki.notefy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

  private final UserService userService;
  private final PostService postService;

  @GetMapping("/{username}")
  public ResponseEntity<AppUserDto> getDetailsForUser(@PathVariable String username) throws IOException {

    AppUser appUser = userService.getByUsernameOrNull(username);

    AppUserDto appUserDto = new AppUserDto(appUser.getFirstName(), appUser.getLastName(), appUser.getUsername(),
        0, appUser.getEmail(), appUser.getPhoneNumber(), postService.findPostsByAuthor(appUser.getUsername()));

    return ResponseEntity.ok(appUserDto);
  }
}
