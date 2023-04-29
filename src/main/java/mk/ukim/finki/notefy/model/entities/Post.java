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

    //Date of creating the post
    @CreationTimestamp
    private Date dateOfCreation;

    //All Users who got the post
    @ManyToMany
    @Null
    private List<AppUser> assignedUsers;

    //Records of all the ratings by assigned users
    @ElementCollection
    @MapKeyColumn(name="user_id")
    @Column(name="rating")
    @CollectionTable(name = "post_ratings",
            joinColumns = @JoinColumn(name = "post_id"))
    private Map<Long,Integer> ratingsByUser=new HashMap<Long,Integer>();

    @Null
    private Double finalRating;

}
