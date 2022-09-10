package message;

import java.util.Map;

//class for updating any data in db
public class InstanceEdit extends RequestResponse {
    private String tableName;
    private String columnNewValue; //e.g. name=alex, user_id=1291
    private String conditionValue; //e.g. class_id=3L

    public InstanceEdit(RequestResponseType requestResponseType, String tableName, String columnNewValue, String conditionValue) {
        super(requestResponseType);
        this.tableName = tableName;
        this.columnNewValue = columnNewValue;
        this.conditionValue = conditionValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnNewValue() {
        return columnNewValue;
    }

    public void setColumnNewValue(String columnNewValue) {
        this.columnNewValue = columnNewValue;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
