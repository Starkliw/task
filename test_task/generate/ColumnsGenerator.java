package test_task.generate;

import test_task.database.ConnectionToDatabase;
import test_task.Employee;

import java.sql.*;

public final class ColumnsGenerator {
    private ColumnsGenerator(){}
    private static final StringBuilder sqlStatement = new StringBuilder();

    static{
        sqlStatement.append("INSERT INTO employees SELECT t_sequence.NEXTVAL, name, date_of_birth, sex FROM( ");
        for(int i = 0; i < 999; i++){
            String select = " SELECT INITCAP(?) name, TO_DATE(?) date_of_birth, INITCAP(?) sex FROM dual UNION ALL ";
            sqlStatement.append(select);
        }
        sqlStatement.append(" SELECT INITCAP(?) name, TO_DATE(?) date_of_birth, INITCAP(?) sex FROM dual)");
    }

    private static void sendBatchEmployeesToDataBase(Employee[] empBuff) throws SQLException {
        try(Connection connection = ConnectionToDatabase.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlStatement.toString());
            for(int i = 0, k = 1; i < 1000; i++) {
                statement.setString(k, empBuff[i].getName());
                k++;
                statement.setDate(k, empBuff[i].getDate());
                k++;
                statement.setString(k, empBuff[i].getSex());
                k++;
            }
            statement.executeUpdate();
        }
    }
    public static void GenerationAMillionRowsForEmployeesTable() throws SQLException {
        Employee[] empBuff = new Employee[1000];
        boolean isFirstHundredEmployeesSent = false;
        for(int l = 0; l < 100; l++){
            Employee employee = new Employee();
            String sex = "Male";
            String familyName = EmployeeGenerator.randMaleFamilyNameStartsWithF();
            String name = EmployeeGenerator.randName(sex);
            String lastName = EmployeeGenerator.randLastName(sex);

            employee.setName(name + " " + familyName + " " + lastName);
            employee.setSex(sex);
            employee.setDate(EmployeeGenerator.randDate());

            empBuff[l] = employee;
            isFirstHundredEmployeesSent = true;
        }
            for (int k = 0; k < 1000; k++) {
                for (int i = 0; i < 1000; i++) {
                    if(isFirstHundredEmployeesSent) {i+=100; isFirstHundredEmployeesSent = false;}
                    Employee employee = new Employee();
                    String sex = EmployeeGenerator.randSex();
                    String familyName = EmployeeGenerator.randFamilyName(sex);
                    String name = EmployeeGenerator.randName(sex);
                    String lastName = EmployeeGenerator.randLastName(sex);

                    employee.setName(name + " " + familyName + " " + lastName);
                    employee.setSex(sex);
                    employee.setDate(EmployeeGenerator.randDate());

                    empBuff[i] = employee;
                }
                sendBatchEmployeesToDataBase(empBuff);
        }
    }

}
