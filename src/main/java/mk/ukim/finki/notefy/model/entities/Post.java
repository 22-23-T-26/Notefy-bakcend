package mk.ukim.finki.notefy.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    public long price;
    private String url;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private AppUser createdBy;
    public boolean paymentFlag;
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dateOfCreation;

    private Double finalRating;

}
