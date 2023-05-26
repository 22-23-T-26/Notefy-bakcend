package mk.ukim.finki.notefy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String createdBy;

    private String content;

    private LocalDateTime createdTime;

    private List<CommentDto> replies = new ArrayList<>();

}
