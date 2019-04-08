package norvina.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Non-existent id.")
public class IdNotFoundException extends RuntimeException {

    private int statusCode;

    public IdNotFoundException() {
        this.statusCode = 404;
    }

    public IdNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
