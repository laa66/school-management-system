package message;

public class Default extends RequestResponse {
    private String text;

    public Default(RequestResponseType requestResponseType) {
        super(requestResponseType);
    }

    public Default(RequestResponseType requestResponseType, String text) {
        super(requestResponseType);
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
