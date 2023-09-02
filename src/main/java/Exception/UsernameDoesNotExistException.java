package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameDoesNotExistException extends RuntimeException {
    public UsernameDoesNotExistException(String message) {
        super(message);
    }
}