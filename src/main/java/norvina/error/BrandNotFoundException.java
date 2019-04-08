package norvina.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found!")
public class BrandNotFoundException  extends RuntimeException{

    private int statusCode;

    public BrandNotFoundException() {
        this.statusCode = 404;
    }

    public BrandNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
