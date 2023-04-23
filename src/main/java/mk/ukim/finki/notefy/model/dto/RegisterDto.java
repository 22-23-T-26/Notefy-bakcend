package mk.ukim.finki.notefy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "Username must be provided")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotNull(message = "Password must be provided")
    @NotBlank(message = "Password must not be blank")
    private String password;


    // TODO ADD OTHER NEEDED FIELDS
}
