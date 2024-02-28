package test_task;

import test_task.database.InitDataBase;
import test_task.database.TableView;
import test_task.exceptions.WrongArgumentException;
import test_task.generate.ColumnsGenerator;

import java.sql.*;
import java.time.format.DateTimeParseException;

public final class Task {
    private Task(){}
    public static void runChooseProgram(String[] args) throws SQLException {
        switch (args[0]){
            case "1":
                InitDataBase.createEmployeesTable();
                break;
            case "2":
                if(args.length < 4 || (!args[3].equalsIgnoreCase("Male") && !args[3].equalsIgnoreCase("Female")) ||
                        args[2].length() != 10 || args[1].chars().filter(Character::isWhitespace).count() != 2) {throw new WrongArgumentException();}
                Employee employee;
                try {employee = new Employee(args[1], args[2], args[3]);}
                catch (DateTimeParseException dt){throw new WrongArgumentException();}
                employee.sendToDataBase();
                break;
            case "3":
                TableView.showDistinctAndSortedByNameValuesFromEmployeesTable();
                break;
            case "4":
                System.out.println("Генерирование миллиона работников и их запись в базу данных... Процесс занимает около 4-х минут");
                ColumnsGenerator.GenerationAMillionRowsForEmployeesTable();
                break;
            case "5":
                System.out.println("Seconds: " + TableView.showSelectStatementExecutionTimeInSeconds());
                break;
            default: throw new WrongArgumentException();
        }
    }

    public static void main(String[] args) throws SQLException{
        if (args.length == 0){throw new WrongArgumentException();}
        runChooseProgram(args);
    }
}
