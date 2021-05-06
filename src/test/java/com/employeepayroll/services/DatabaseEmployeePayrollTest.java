package com.employeepayroll.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

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
        employeePayrollService.updateEmployeeBasicPay("Terisa",3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        System.out.println(employeePayrollData);
        Assertions.assertTrue(result);
    }
}