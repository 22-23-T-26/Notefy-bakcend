package mk.ukim.finki.notefy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String title;
    private String description;
    public long price;
    private String url;
    private Long subject;
    private boolean paymentFlag;
    private String category;
}
