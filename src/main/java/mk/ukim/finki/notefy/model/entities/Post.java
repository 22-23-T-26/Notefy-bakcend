package mk.ukim.finki.notefy.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private String url;
    @ManyToOne
    @Null
    private Subject subject;
    @ManyToOne
    private AppUser createdBy;
    public boolean paymentFlag;
    @Enumerated(value = EnumType.STRING)
    private Category category;

    //Date of creating the post
    @CreationTimestamp
    @Null
    private Date dateOfCreation;

    @Null
    private Double finalRating;

}
