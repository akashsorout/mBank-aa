package in.co.bytehub.mbankaa.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorizationFailedException extends RuntimeException {

    private static final String Default_Message = "Forbidden Access";

    public AuthorizationFailedException() {
        super(Default_Message);
    }

    public AuthorizationFailedException(String message) {
        super(message);
    }
}
