package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractInstanceDAO {
    protected Connection connection;
    protected Statement statement;

    public AbstractInstanceDAO() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "password");
    }

    public void close() throws SQLException {
        connection.close();
        statement.close();
    }

    public List<Integer> getAllID() throws SQLException {
        List<Integer> allID = new ArrayList<>();
        String query = "SELECT user_id FROM users\n" +
                "UNION\n" +
                "SELECT operation_id FROM operation_history;";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            allID.add(resultSet.getInt(1));
        }
        return allID;
    }
}
