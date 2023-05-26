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
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser createdUser;

    private String title;
    private String description;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    @JsonIgnore
    public Comment getLastComment() {
        if(comments != null && comments.size() > 0) {
            return comments.get(comments.size() - 1);
        }
        return null;
    }
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();
}
