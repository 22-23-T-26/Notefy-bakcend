package mk.ukim.finki.notefy.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException{

    public BadRequest(String message) {
        super(message);
    }
}
