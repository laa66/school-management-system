package message;

import instance.Subject;

//class for reading any information from db
public class InstanceRead extends RequestResponse {
    private String tableName;
    private String columnValue; //e.g. name=john, person_weight>50
    private Subject subjectValue;
    private String usernameValue;

    public InstanceRead(RequestResponseType requestResponseType, String tableName, String columnValue) {
        super(requestResponseType);
        this.tableName = tableName;
        this.columnValue = columnValue;
    }

    //constructor for reading user marks by subject/all
    public InstanceRead(RequestResponseType requestResponseType, String tableName, String columnValue, Subject subjectValue, String usernameValue) {
        super(requestResponseType);
        this.tableName = tableName;
        this.columnValue = columnValue; //student/class
        this.subjectValue = subjectValue;
        this.usernameValue = usernameValue;
    }

    //getters and setters
    public String getTableName() {
        return tableName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public Subject getSubjectValue() {
        return subjectValue;
    }

    public String getUsernameValue() {
        return usernameValue;
    }
}
