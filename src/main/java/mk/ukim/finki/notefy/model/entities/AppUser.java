package mk.ukim.finki.notefy.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    public String getRole() {
        return role.toString();
    }
}
