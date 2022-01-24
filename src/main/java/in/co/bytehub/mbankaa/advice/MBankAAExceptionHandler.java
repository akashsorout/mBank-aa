package in.co.bytehub.mbankaa.advice;

import in.co.bytehub.mbankaa.response.ErrorResponse;
import in.co.bytehub.mbankaa.security.exception.AuthenticationFailedException;
import in.co.bytehub.mbankaa.security.exception.AuthorizationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MBankAAExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleUnAuthenticatedRequest(AuthenticationFailedException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse().setMessage(ex.getMessage()));
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<Object> handleUnAuthorizationRequest(AuthorizationFailedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse().setMessage(ex.getMessage()));
    }
}
