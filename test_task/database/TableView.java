package test_task.database;

import test_task.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class TableView {
    private TableView(){}
    public static void showDistinctAndSortedByNameValuesFromEmployeesTable() throws SQLException {
        try(Connection connection = ConnectionToDatabase.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT name, " +
                    "TO_CHAR(TO_DATE(date_of_birth, 'DD-MON-RR'), 'DD-MM-YYYY') AS date_of_birth, sex FROM employees e1 ORDER BY name");
            while(resultSet.next()) {
                Employee employee = new Employee(resultSet.getString("NAME"), resultSet.getString("DATE_OF_BIRTH"),
                        resultSet.getString("SEX"));
                System.out.println("Name: " + employee.getName() + " | Date of birth: " + employee.getDate() +
                        " | Gender: " + employee.getSex() + " | Full Years: " + employee.calcFullYears());
            }
        }
    }
    public static double showSelectStatementExecutionTimeInSeconds() throws SQLException {
        try(Connection connection = ConnectionToDatabase.getConnection()){
            Statement statement = connection.createStatement();
            long startTime = System.nanoTime();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM (SELECT * FROM employees WHERE name like '% Ф% %' AND sex = 'Male')");
            resultSet.next();
            System.out.println(resultSet.getInt("COUNT(*)"));
            long endTime = System.nanoTime();
            return ((double) (endTime - startTime) / 1000000 / 1000.0);
        }
    }
}
