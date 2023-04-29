package mk.ukim.finki.notefy.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;


    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String firstName;

    private String lastName;

    private String phoneNumber;


    //All Posts that the User is assigning
    @ManyToMany(mappedBy = "assignedUsers")
    @Null
    private List<Post> postsOfUsers;

    public String getRole() {
        return role.toString();
    }

}
