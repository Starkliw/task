package test_task.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionToDatabase {
    private ConnectionToDatabase(){}
    public static Connection getConnection() throws SQLException {
        String userName = "task";
        String password = "task";
        String connectionURL = "jdbc:oracle:thin:@localhost:1521:XE";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found");
        }
        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
