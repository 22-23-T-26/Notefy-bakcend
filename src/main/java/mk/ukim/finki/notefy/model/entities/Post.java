package mk.ukim.finki.notefy.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    public long price;
    private File file;
    private String url;
    private File picture;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private AppUser createdBy;
    public boolean paymentFlag;
    @Enumerated(value = EnumType.STRING)
    private Category category;
}
