package mk.ukim.finki.notefy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.notefy.model.entities.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionDto {

        private Long id;

        private String createdBy;

        private String title;
        private String description;

        private LocalDateTime createdTime;

        private List<Comment> comments = new ArrayList<>();

        private Long numberOfLikes;
        private Long numberOfComments;

        private Boolean isLiked;

}
