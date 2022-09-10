package message;

//class for removing instances from db
public class InstanceRemove extends RequestResponse {
    private String tableName;
    private String columnValue; //any column-value by which you want to delete, logical exp is in the middle of string e.g. name=john, user_id>5

    public InstanceRemove(RequestResponseType requestResponseType, String tableName ,String columnValue) {
        super(requestResponseType);
        this.tableName = tableName;
        this.columnValue = columnValue;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnValue() {
        return columnValue;
    }
}
