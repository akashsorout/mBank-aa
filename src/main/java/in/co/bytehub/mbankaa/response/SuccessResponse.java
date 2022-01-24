package in.co.bytehub.mbankaa.response;


public class SuccessResponse {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public SuccessResponse setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public SuccessResponse(String userName) {
        this.userName = userName;
    }
}
