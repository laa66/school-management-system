package message;

import java.util.Map;

//class for reading any information from db
public class InstanceRead extends RequestResponse {
    private String tableName;
    private String columnValue; //e.g. name=john, person_weight>50

    public InstanceRead(RequestResponseType requestResponseType, String tableName, String columnValue) {
        super(requestResponseType);
        this.tableName = tableName;
        this.columnValue = columnValue;
    }

    //getters and setters
    public String getTableName() {
        return tableName;
    }

    public String getColumnValue() {
        return columnValue;
    }
}
