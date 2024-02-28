package test_task.database;

import test_task.exceptions.SequenceFoundException;
import test_task.exceptions.TableFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public final class InitDataBase {
    private InitDataBase(){}
    public static void createEmployeesTable() throws SQLException {
        try(Connection connection = ConnectionToDatabase.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE employees(" +
                            "id INTEGER PRIMARY KEY NOT NULL," +
                            "name VARCHAR2(100)," +
                            "date_of_birth date," +
                            "sex VARCHAR2(6))");
            createSequenceForEmployeesTable(statement);
            createIndexForEmployeesTable(statement);
            System.out.println("Таблица employees создана");

        } catch (SQLSyntaxErrorException e) {
            if (e.getErrorCode() == 955)
                throw new TableFoundException("Таблица employees уже существует");
            else
                throw e;
        }
    }
    private static void createSequenceForEmployeesTable(Statement statement) throws SQLException{
        try {
            statement.execute(
                    "CREATE SEQUENCE t_sequence " +
                            "START WITH 1 " +
                            "INCREMENT BY 1");
        } catch (SQLSyntaxErrorException e) {
            if (e.getErrorCode() == 955)
                throw new SequenceFoundException("Sequence для таблицы employees уже существует");
            else
                throw e;
        }
    }
    private static void createIndexForEmployeesTable(Statement statement) throws SQLException{
        try {
            statement.execute("CREATE INDEX emp_ind_name ON employees(name)");
            statement.execute("CREATE INDEX emp_ind_date_of_birth ON employees(date_of_birth)");
            statement.execute("CREATE BITMAP INDEX emp_bit_ind_sex ON employees(sex)");
        } catch (SQLSyntaxErrorException e) {
            if (e.getErrorCode() == 955)
                throw new SequenceFoundException("Индексы для таблицы employees уже существуют");
            else
                throw e;
        }
    }
}
