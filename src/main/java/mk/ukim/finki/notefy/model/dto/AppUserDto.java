package mk.ukim.finki.notefy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.notefy.model.entities.Post;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

  private String firstName;

  private String lastName;

  private String username;

  private double rating;

  private String password;

  private String email;

  private String phoneNumber;

  private List<Post> postsOfUsers;

}
