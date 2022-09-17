package database;

import instance.Marks;
import instance.Operation;
import instance.Subject;
import instance.Transferable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    //added reading all student grades, averages and specific subject grades, averages
    // TODO: 2022-09-17 implement fetching marks and avg marks for classes
    // TODO: 2022-09-17 implement fetching ending semester grade (average of average grades from all student subjects)
    public Marks getUserMarksBySubject(Subject subjectValue, String usernameValue) throws SQLException {
        String query;
        if (subjectValue.equals(Subject.ALL)){
            query = "SELECT users.user_id, users.first_name, users.last_name, operation_history.mark, operation_history.date, operation_history.subject, operation_history.note\n" +
                    "FROM operation_history\n" +
                    "INNER JOIN users \n" +
                    "ON operation_history.user_id = users.user_id\n" +
                    "WHERE username = '" + usernameValue + "';";
        }
        else {
            query = "SELECT users.user_id, users.first_name, users.last_name, operation_history.mark, operation_history.date, operation_history.subject, operation_history.note\n" +
                    "FROM operation_history\n" +
                    "INNER JOIN users \n" +
                    "ON operation_history.user_id = users.user_id\n" +
                    "WHERE username = '" + usernameValue + "' AND subject = '" + subjectValue + "';";
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Operation> operations = new ArrayList<>();
        while (resultSet.next()) {
            Operation operation = new Operation();
            operation.setUserId(resultSet.getInt(1));
            operation.setFirstName(resultSet.getString(2));
            operation.setLastName(resultSet.getString(3));
            operation.setMark(resultSet.getString(4));
            operation.setDate(resultSet.getDate(5));
            operation.setSubject(Subject.valueOf(resultSet.getString(6)));
            operation.setNote(resultSet.getString(7));
            operations.add(operation);
        }
        Marks marks = new Marks();
        marks.setListOfMarks(operations);
        return marks;
    }


    public Marks getClassMarksBySubject(Subject subjectValue, String usernameValue) {
        return null;
    }

    public Marks getUserAverageBySubject(Subject subjectValue, String usernameValue) throws SQLException {
        String query;
        if (subjectValue.equals(Subject.ALL)) {
            query = "SELECT users.user_id, users.first_name, users.last_name, avg(operation_history.mark) AS 'avg_mark', operation_history.subject\n" +
                    "FROM operation_history\n" +
                    "INNER JOIN users \n" +
                    "ON operation_history.user_id = users.user_id\n" +
                    "WHERE username = '" + usernameValue + "' GROUP BY operation_history.subject;";
        } else {
            query = "SELECT users.user_id, users.first_name, users.last_name, avg(operation_history.mark) AS 'avg_mark', operation_history.subject\n" +
                    "FROM operation_history\n" +
                    "INNER JOIN users \n" +
                    "ON operation_history.user_id = users.user_id\n" +
                    "WHERE username = '" + usernameValue + "' AND subject = '" + subjectValue + "';";
        }
        List<Operation> operations = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Operation operation = new Operation();
            operation.setUserId(resultSet.getInt(1));
            operation.setFirstName(resultSet.getString(2));
            operation.setLastName(resultSet.getString(3));
            operation.setAverageMark(resultSet.getString(4));
            operation.setSubject(Subject.valueOf(resultSet.getString(5)));
            operations.add(operation);
        }
        Marks marks = new Marks();
        marks.setListOfMarks(operations);
        return marks;
    }

    public Marks getClassAverageBySubject(Subject subjectValue, String usernameValue) {
        return null;
    }
}
