package com.employeepayroll.services;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    private static PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollDBService() {
    }

    public static EmployeePayrollDBService getInstance(){
        if(employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "Sainath@8801";
        Connection connection;
        System.out.println("Connecting to database:"+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successful!!!"+connection);
        return connection;
    }



    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_service";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                double basicPay = result.getDouble("basic_pay");
                LocalDate startDate = result.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,salary,basicPay,startDate));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollList;
    }

     int updateEmployeePayrollData(String name, Double salary){
        return this.updateEmployeeDataUsingStatement(name,salary);
    }

    int updateEmployeePayrollDataForBasicPay(String name, double basicPay){
        return this.updateEmployeeDataUsingStatementForBasicPay(name, basicPay);
    }

    private int updateEmployeeDataUsingStatementForBasicPay(String name, double basicPay) {
        String sql = String.format("UPDATE employee_service set basic_pay = %.2f where name = '%s';", basicPay,name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private int updateEmployeeDataUsingStatement(String name, Double salary){
        String sql = String.format("UPDATE employee_service set salary = %.2f where name = '%s';", salary,name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet result){
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try{
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            double salary = result.getDouble("salary");
            double basicPay = result.getDouble("basic_pay");
            LocalDate startDate = result.getDate("start").toLocalDate();
            employeePayrollList.add(new EmployeePayrollData(id,name,salary,basicPay,startDate));
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        return employeePayrollList;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name)
    {
        List<EmployeePayrollData> employeePayrollList = null;
        if (employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try
        {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return employeePayrollList;
    }

    private void prepareStatementForEmployeeData()
    {
        try
        {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_service WHERE name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
