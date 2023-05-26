package mk.ukim.finki.notefy.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser createdUser;

    private String content;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    public void addComment(Comment comment) {
        this.replies.add(comment);
    }
    @JsonIgnore
    public Comment getLast() {
        if(replies != null && replies.size() > 0) {
            return replies.get(replies.size() - 1);
        }
        return null;
    }
    // Getters and setters
}
