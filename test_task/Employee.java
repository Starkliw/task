package test_task;

import test_task.database.ConnectionToDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Employee {
    public Employee(){}
    public Employee(String name, String date, String sex){
        this.name = name;
        this.date = Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.sex = sex;
    }
    private String name;
    private Date date;
    private String sex;

    public String getName() {return name;}
    public Date getDate() {return date;}
    public String getSex() {return sex;}

    public void setName(String name) {this.name = name;}
    public void setDate(String date) {this.date = Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));}

    public void setSex(String sex) {this.sex = sex;}

    public void sendToDataBase() throws SQLException{
        try(Connection connection = ConnectionToDatabase.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO employees (ID, NAME, DATE_OF_BIRTH, SEX)" +
                    " VALUES (t_sequence.nextval, INITCAP(?), TO_DATE(?, 'DD-MM-YYYY'), INITCAP(?))");
            statement.setString(1, this.getName());
            statement.setDate(2, this.getDate());
            statement.setString(3, this.getSex());
            statement.executeUpdate();
            System.out.println("Запись в Базе данных была создана");
        }
    }
    public int calcFullYears(){
        return Period.between(date.toLocalDate(), LocalDate.now()).getYears();
    }
}
