package message;

public enum RequestResponseType {
    REQUEST_VERIFICATION, //server
    VERIFICATION_ACCEPTED, //server
    VERIFICATION_DATA, //client
    READ_INSTANCE, //client
    CREATE_INSTANCE, //client-server
    EDIT_INSTANCE, //client
    REMOVE_INSTANCE, //client
    DEFAULT, //client-server
    ERROR //server
}
