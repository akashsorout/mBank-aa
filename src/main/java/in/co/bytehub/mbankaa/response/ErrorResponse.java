package in.co.bytehub.mbankaa.response;


public class ErrorResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
