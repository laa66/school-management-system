package database;

import instance.User;
import java.sql.*;

public class UserDAO extends AbstractInstanceDAO {

    public UserDAO() throws SQLException {
        super();
    }

    public User getUser(String columnValue) throws SQLException {
        User user = new User();
        String[] splitColumnValue = columnValue.split("=");
        String column = splitColumnValue[0];
        String value = splitColumnValue[1];
        String query = "SELECT * FROM users WHERE " + column + " = " + "'" + value + "';";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        user.setUserId(resultSet.getInt("user_id"));
        user.setClassId(resultSet.getString("class_id"));
        user.setUsername(resultSet.getString("username"));
        user.setHashPassword(resultSet.getString("hash_password"));
        user.setFirstname(resultSet.getString("first_name"));
        user.setLastname(resultSet.getString("last_name"));
        user.setPersonalIdNumber(resultSet.getInt("personal_id_number"));
        user.setBirthDate(resultSet.getDate("birth_date"));
        user.setJoinDate(resultSet.getDate("join_date"));
        user.setTeacher(resultSet.getBoolean("teacher"));
        close();
        return user;
    }

    public boolean createUser(User user, int uniqueID) throws SQLException {
        String query = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, uniqueID);
        preparedStatement.setString(2, user.getClassId());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getHashPassword());
        preparedStatement.setString(5, user.getFirstname());
        preparedStatement.setString(6, user.getLastname());
        preparedStatement.setString(7, String.valueOf(user.getPersonalIdNumber()));
        preparedStatement.setDate(8, new Date(user.getBirthDate().getTime()));
        preparedStatement.setDate(9, new Date(user.getJoinDate().getTime()));
        preparedStatement.setBoolean(10, user.isTeacher());
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows inserted: " + result);
        return true;
    }

    public boolean editUser(String columnNewValue, String conditionValue) throws SQLException {
        String column = columnNewValue.split("=")[0];
        String newValue = columnNewValue.split("=")[1];
        String condition = conditionValue.split("=")[0];
        String value = conditionValue.split("=")[1];

        String query = "UPDATE users SET " + column + " = ? WHERE " + condition + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, newValue);
        preparedStatement.setString(2, value);
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows updated: " + result);
        return true;
    }

    public boolean removeUser(String columnValue) throws SQLException {
        String column = columnValue.split("=")[0];
        String value = columnValue.split("=")[1];
        String query = "DELETE FROM users WHERE " + column + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        int result = preparedStatement.executeUpdate();
        System.out.println("Rows deleted: " + result);
        return true;
    }

}
