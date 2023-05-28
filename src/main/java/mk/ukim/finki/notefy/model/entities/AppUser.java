package mk.ukim.finki.notefy.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;
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

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @JsonIgnore
    public String getFullName() {
        String fullName = "";
        if (this.firstName != null && !this.firstName.isBlank()) {
            fullName += firstName;
        }
        if (this.lastName != null && !this.lastName.isBlank()) {
            fullName += (!fullName.isBlank() ? " " : "" )+ this.lastName;
        }
        return fullName;
    }

    @OneToMany(mappedBy = "createdUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Discussion> createdDiscussions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Like> createdLikes = new ArrayList<>();

    @OneToMany(mappedBy = "createdUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> createdComments = new ArrayList<>();

    public String getRole() {
        return role == null ? "" : role.toString();
    }

}
