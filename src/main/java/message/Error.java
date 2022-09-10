package message;

public class Error extends RequestResponse {
    private Exception exception;

    public Error(RequestResponseType requestResponseType, Exception exception) {
        super(requestResponseType);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
