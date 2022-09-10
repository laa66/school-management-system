package message;

import java.io.Serializable;

public abstract class RequestResponse implements Serializable {
    private RequestResponseType requestResponseType;

    public RequestResponse(RequestResponseType requestResponseType) {
        this.requestResponseType = requestResponseType;
    }

    public RequestResponseType getRequestType() {
        return requestResponseType;
    }
}
