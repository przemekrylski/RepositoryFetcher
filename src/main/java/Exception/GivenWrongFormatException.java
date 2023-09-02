package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class GivenWrongFormatException extends RuntimeException {
    public GivenWrongFormatException(String message) {
        super(message);
    }
}