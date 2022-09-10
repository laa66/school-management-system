package database;

import instance.ClassInstance;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassInstanceDAO extends AbstractInstanceDAO {

    public ClassInstanceDAO() throws SQLException {
        super();
    }

    public ClassInstance getClassInstance(String columnValue) throws SQLException {
        ClassInstance classInstance = new ClassInstance();
        String[] splitColumnValue = columnValue.split("=");
        String column = splitColumnValue[0];
        String value = splitColumnValue[1];
        String query = "SELECT * FROM classes WHERE " + column + " = " + "'" + value + "';";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        classInstance.setClassId(resultSet.getString("class_id"));
        classInstance.setTeacherId(resultSet.getInt("teacher_id"));
        classInstance.setNumberOfStudents(resultSet.getInt("students_count"));
        close();
        return classInstance;
    }


    public boolean createClassInstance(ClassInstance classInstance) throws SQLException {
        String query = "INSERT INTO classes VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, classInstance.getClassId());
        preparedStatement.setInt(2, classInstance.getTeacherId());
        preparedStatement.setInt(3, classInstance.getNumberOfStudents());
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows inserted: " + result);
        return true;
    }

    public boolean editClassInstance(String columnNewValue, String conditionValue) throws SQLException {
        String column = columnNewValue.split("=")[0];
        String newValue = columnNewValue.split("=")[1];
        String condition = conditionValue.split("=")[0];
        String value = conditionValue.split("=")[1];
        String query = "UPDATE classes SET " + column + " = ? WHERE " + condition + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newValue);
        preparedStatement.setString(2, value);
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows updated: " + result);
        return true;
    }

    public boolean removeClassInstance(String columnValue) throws SQLException {
        String column = columnValue.split("=")[0];
        String value = columnValue.split("=")[1];
        String query = "DELETE FROM classes WHERE " + column + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows deleted: " + result);
        return true;
    }

}
