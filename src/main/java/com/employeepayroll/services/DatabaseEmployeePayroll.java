package com.employeepayroll.services;

import java.sql.*;
import java.util.Enumeration;

public class DatabaseEmployeePayroll{
    public static void main(String[] args) {
        //declared jdbcUrl, username, password
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "Sainath@8801";
        Connection connection;

        try{
            //calling class for mysql driver
            //loading drivers using forName() method
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        }catch (ClassNotFoundException e){
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        listDrivers();
        try{
            System.out.println("Connecting to database:"+jdbcURL);
            connection = DriverManager.getConnection(jdbcURL,userName,password);

            System.out.println("Connection is successful!!!"+connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //to check the list of drivers present
    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while(driverList.hasMoreElements()){
            Driver driverClass = driverList.nextElement();
            System.out.println(" "+driverClass.getClass().getName());
        }
    }
}
