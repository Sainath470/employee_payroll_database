package com.employeepayroll.services;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayrollDBService {

    //declared private variables
    private static PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    //created  default constructor
    public EmployeePayrollDBService() {
    }

    /**
     * create singleton design principle to get single instance
     * @return employeePayrollDBService
     */
    public static EmployeePayrollDBService getInstance(){
        if(employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    /**
     * created getConnection() method to make connection with mysql database
     * @return connection
     * @throws SQLException sql exception
     */
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll_service?useSSL=false";
        String userName = "root";
        String password = "Sainath@8801";
        Connection connection;
        System.out.println("Connecting to database:"+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successful!!!"+connection);
        return connection;
    }

    /**
     * created readData() to read data from database table
     * added try catch block to throw sql exception
     * @return employeePayrollList
     */
    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll;";
        return this.getEmployeePayrollDataUsingDB(sql);
    }
//

    /**
     * created getEmployeePayrollDataForDateRange method to retrieve data from database for particular date range
     * @param startDate in local date format
     * @param endDate in local date format
     * @return this.getEmployeePayrollDataUsingDB(sql);
     */
    public List<EmployeePayrollData> getEmployeePayrollDataForDateRange(LocalDate startDate, LocalDate endDate)
    {
        String sql = String.format("SELECT * FROM  employee_payroll WHERE START BETWEEN '%s' AND '%s';", Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getEmployeePayrollDataUsingDB(sql);
    }

    /**
     * created getEmployeePayrollDataUsingDB() method to retrieve data from database
     * by taking sql query as input
     * @param sql getEmployeePayrollDateRange
     * @return employeePayrollList
     */
    private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql)
    {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    /**
     * created method to update employeePayrollData
     * @param name takes the name as input
     * @param salary and salary as the input
     * @return values to updateEmployeeDataUsingStatement method
     */
     public int updateEmployeePayrollData(String name, Double salary){
        return this.updateEmployeeDataUsingStatementForSalary(name,salary);
    }

    /**
     * created method to update employeePayrollData basic_pay
     * @param name takes  input
     * @param basicPay takes input
     * @return values to updateEmployeeDataUsingStatementForBasicPay method
     */
    public int updateEmployeePayrollDataForBasicPay(String name, double basicPay){
        return this.updateEmployeeDataUsingStatementForBasicPay(name, basicPay);
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

    /**
     * created updateEmployeeDataUsingStatementForBasicPay method to update data in database by using sql query
     * added try and catch block to throw sql exception
     * @param name name
     * @param basicPay basic pay
     * @return statement.executeUpdate(sql)
     */
    private int updateEmployeeDataUsingStatementForBasicPay(String name, double basicPay) {
        String sql = String.format("UPDATE employee_payroll set basic_pay = %.2f where name = '%s';", basicPay,name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * created updateEmployeeDataUsingStatementForSalary method to update data in database by using sql query
     * added try and catch block to throw sql exception
     * @param name name
     * @param salary salary
     * @return statement.executeUpdate(sql)
     */
    private int updateEmployeeDataUsingStatementForSalary(String name, Double salary){
        String sql = String.format("UPDATE employee_payroll set salary = %.2f where name = '%s';", salary,name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * created private getEmployeePayrollData method to get all data from database table
     * added try and catch block to throw sql exception
     * @param result resultSet
     * @return employeePayrollDataList
     */
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet result){
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try{
            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                double basicPay = result.getDouble("basic_pay");
                LocalDate startDate = result.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,salary,startDate));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return employeePayrollList;
    }

    /**
     * prepareStatementForEmployeeData method for single query to get the data from database
     * added try and catch block to throw sql exception
     */
    private void prepareStatementForEmployeeData() {
        try
        {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll WHERE name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private List<EmployeePayrollData> getEmployeePayrollDataForDateRange(ResultSet resultSet)
    {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try
        {
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double  basic_pay = resultSet.getDouble("basic_pay");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary,  startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public Map<String, Double> readAverageSalaryGroupByGender(){
        String sql = "SELECT gender, AVG(salary) as salary FROM employee_payroll GROUP BY gender;";
        Map<String, Double> averageSalaryGroupByGender = new HashMap<>();
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                averageSalaryGroupByGender.put(gender, salary);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return averageSalaryGroupByGender;
    }

    public Map<String, Double> readMinimumSalaryGroupByGender(){
        String sql = "SELECT gender, MIN(salary) as salary FROM employee_payroll GROUP BY gender;";
        Map<String, Double> minimumSalaryGroupByGender = new HashMap<>();
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                minimumSalaryGroupByGender.put(gender, salary);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return minimumSalaryGroupByGender;
    }

    public Map<String, Double> readMaximumSalaryGroupByGender() {
        String sql = "SELECT gender, Max(salary) as salary FROM employee_payroll GROUP BY gender;";
        Map<String, Double >maximumSalaryGroupByGender = new HashMap<>();
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                maximumSalaryGroupByGender.put(gender, salary);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return maximumSalaryGroupByGender;
    }

    public Map<String, Double> readCountOfEmployeesGroupByGender() {
        String sql = "SELECT gender, COUNT(*) as count FROM employee_payroll GROUP BY gender;";
        Map<String, Double >countOfEmployeeGroupByGender = new HashMap<>();
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                String gender = resultSet.getString("gender");
                double count = resultSet.getDouble("count");
                countOfEmployeeGroupByGender.put(gender, count);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return countOfEmployeeGroupByGender;
    }


    public EmployeePayrollData addEmployeePayroll(String name, double basic_pay, LocalDate start, String gender)
    {
        int employeeID = -1;
        Connection connection = null;
        EmployeePayrollData employeePayrollData = null;
        try {
            connection = this.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement())
        {
            String sql = String.format("INSERT INTO employee_payroll (name, gender, basic_pay, start)" +
                    "VALUES ('%s', '%s', '%s', '%s')", name, gender, basic_pay, Date.valueOf(start));
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1)
            {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) employeeID = resultSet.getInt(1);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            try {
                connection.rollback();
                return employeePayrollData;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement())
        {
            double deductions = basic_pay * 0.2;
            double taxablePay = basic_pay - deductions;
            double tax = taxablePay * 0.1;
            double netPay = basic_pay - tax;
            String sql = String.format("INSERT INTO payroll_details" +
                    "(employee_id, basic_pay, deductions, taxable_pay, taxa, net_pay) VALUES" +
                    "(%s, %s, %s, %s, %s, %s)", employeeID, basic_pay, deductions, taxablePay, tax, netPay);
            int rowAffected = statement.executeUpdate(sql);
            if (rowAffected == 1)
            {
                employeePayrollData = new EmployeePayrollData(employeeID, name, basic_pay, start);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            try {
                connection.rollback();
                return employeePayrollData;
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        try {
            connection.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
            }
        }
        return employeePayrollData;
    }
}



