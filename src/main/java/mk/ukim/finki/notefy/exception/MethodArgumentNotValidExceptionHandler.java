package mk.ukim.finki.notefy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Klasa kade sto se konfigurira strukturata na validaciskite poraki za @Valid na parametri na metodi
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
/*
  This class regenerates the response format during validations (@Valid) annotation on methods arguments
 */
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult(); // Get the object that contains the error
        List<FieldError> fieldErrors = result.getFieldErrors(); // get all fields which aren't in the right format
        return processFieldErrors(fieldErrors, result.getErrorCount()); // process the data (reformat)
    }

    private ErrorDTO processFieldErrors(List<FieldError> fieldErrors, int errorCount) {
        ErrorDTO error = new ErrorDTO(errorCount);
        for (FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
        }
        return error;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class FieldErrorCustom {
        private String field;
        private String message;
        private Object rejectedValue;
    }

    @Getter
    @Setter
    static class ErrorDTO extends ApiErrorDTO {
        private List<FieldErrorCustom> fieldErrors = new ArrayList<>();

        ErrorDTO(int errorCount) {
            super(BAD_REQUEST, String.format("Form validation error. Total: %d error%s.", errorCount, errorCount == 1 ? "" : "s"), "/");
        }

        public void addFieldError(String field, String message, Object rejectedValue) {
            FieldErrorCustom error = new FieldErrorCustom(field, message, rejectedValue);
            fieldErrors.add(error);
        }
    }
}
