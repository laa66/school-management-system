package database;

import instance.Operation;
import instance.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationDAO extends AbstractInstanceDAO {

    public OperationDAO() throws SQLException {
        super();
    }

    public Operation getOperation(String columnValue) throws SQLException {
        Operation operation = new Operation();
        String[] splitColumnValue = columnValue.split("=");
        String column = splitColumnValue[0];
        String value = splitColumnValue[1];
        String query = "SELECT * FROM operation_history WHERE " + column + " = " + "'" + value + "';";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        operation.setOperationId(resultSet.getInt("operation_id"));
        operation.setClassId(resultSet.getString("class_id"));
        operation.setUserId(resultSet.getInt("user_id"));
        operation.setMark(resultSet.getString("mark"));
        operation.setSubject(Subject.valueOf(resultSet.getString("subject").toUpperCase()));
        operation.setDate(resultSet.getDate("date"));
        operation.setNote(resultSet.getString("note"));
        close();
        return operation;
    }


    public boolean createOperation(Operation operation, int uniqueID) throws SQLException {
        String query = "INSERT INTO operation_history VALUES (?,?,?,?,?, now(),?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, uniqueID);
        preparedStatement.setString(2, operation.getClassId());
        preparedStatement.setInt(3, operation.getUserId());
        preparedStatement.setString(4, operation.getMark());
        preparedStatement.setString(5, operation.getSubject().toString());
        preparedStatement.setString(6, operation.getNote());
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows inserted: " + result);
        return true;
    }

    public boolean editOperation(String columnNewValue, String conditionValue) throws SQLException {
        String column = columnNewValue.split("=")[0];
        String newValue = columnNewValue.split("=")[1];
        String condition = conditionValue.split("=")[0];
        String value = conditionValue.split("=")[1];

        String query = "UPDATE operation_history SET " + column + " = ? WHERE " + condition + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newValue);
        preparedStatement.setString(2, value);
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows updated: " + result);
        return true;
    }

    public boolean removeOperation(String columnValue) throws SQLException {
        String column = columnValue.split("=")[0];
        String value = columnValue.split("=")[1];
        String query = "DELETE FROM operation_history WHERE " + column + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows deleted: " + result);
        return true;
    }
}
