package com.employeepayroll.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class DatabaseEmployeePayrollTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> databaseEmployeePayrolls = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        System.out.println(databaseEmployeePayrolls);
        Assertions.assertEquals(3,databaseEmployeePayrolls.size());
    }

    @Test
    public  void  givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB()
    {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa",300000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        System.out.println(employeePayrollData);
       Assertions.assertTrue(result);
    }

    @Test
    public  void  givenNewSalaryForEmployee_WhenUpdated_ShouldMatchWithDB()
    {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        employeePayrollService.updateEmployeeBasicPay("Terisa",300000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        System.out.println(employeePayrollData);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldReturnEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        LocalDate startDate = LocalDate.of(2018, 1,1);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataForDataRange(startDate,endDate);
        Assertions.assertEquals(3,employeePayrollData.size());
    }

    @Test
    public void givenDateRange_WhenAverageSalaryRetrievedGroupByGender_ShouldReturnAverageSalaryByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> averageSalaryGroupByGender = employeePayrollService.getAverageSalaryGroupByGender();
        boolean result = averageSalaryGroupByGender.get("M").equals(2000000.0) && averageSalaryGroupByGender.get("F").equals(300000.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenMinimumSalaryRetrievedGroupByGender_ShouldReturnMinimumSalaryByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> minimumSalaryGroupByGender = employeePayrollService.getMinimumSalaryGroupByGender();
        boolean result = minimumSalaryGroupByGender.get("M").equals(1000000.0) && minimumSalaryGroupByGender.get("F").equals(300000.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenMaximumSalaryRetrievedGroupByGender_ShouldReturnMaximumSalaryByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> maximumSalaryGroupByGender = employeePayrollService.getMaximumSalaryGroupByGender();
        boolean result = maximumSalaryGroupByGender.get("M").equals(3000000.0) && maximumSalaryGroupByGender.get("F").equals(300000.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrievedGroupByGender_ShouldReturnCountOfEmployeesByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> countOfEmployeeGroupByGender = employeePayrollService.getCountOfEmployeeGroupByGender();
        boolean result = countOfEmployeeGroupByGender.get("M").equals(2.0) && countOfEmployeeGroupByGender.get("F").equals(1.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenNewEmployeeDetails_WhenGiven_ShouldBeAddedInDB_AndShouldSyncWithDb(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        employeePayrollService.addEmployeeToPayroll("Bob", 400000.0,LocalDate.now(),"M");
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bob");
        System.out.println(employeePayrollData);
        Assertions.assertTrue(result);
    }
}