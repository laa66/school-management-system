package message;

public class VerificationData extends RequestResponse {
    private String username;
    private String hash;

    public VerificationData(RequestResponseType requestResponseType, String username, String hash) {
        super(requestResponseType);
        this.username = username;
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }
}
