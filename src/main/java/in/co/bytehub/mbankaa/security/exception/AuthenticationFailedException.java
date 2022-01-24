package in.co.bytehub.mbankaa.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationFailedException extends RuntimeException {

    private static final String Default_Message = "Unauthenticated Access";

    public AuthenticationFailedException() {
        super(Default_Message);
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
