package com.employeepayroll.services;

import java.util.List;


public class EmployeePayrollService {

    private List<EmployeePayrollData> employeePayrollList;
    private EmployeePayrollDBService employeePayrollDBService;

    public enum IOServices {CONSOLE_ID, FILE_IO, DB_IO, REST_IO}

    public EmployeePayrollService()
    {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList){
        this();
        this.employeePayrollList = employeePayrollList;
    }

    public List<EmployeePayrollData> readEmployeePayrollData(IOServices ioService)
    {
        if(ioService.equals(IOServices.DB_IO))
            this.employeePayrollList = new EmployeePayrollDBService().readData();
        return this.employeePayrollList;
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name)
    {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.equals(employeePayrollDBService.getEmployeePayrollData(name));
    }

    public void updateEmployeeSalary(String name, Double salary){
        int result = employeePayrollDBService.updateEmployeePayrollData(name,salary);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData;
        employeePayrollData= this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.salary = salary;
    }

    private EmployeePayrollData getEmployeePayrollData(String name){
        return this.employeePayrollList.stream().filter(employeePayrollData ->
                employeePayrollData.name.equals(name)).findFirst().orElse(null);
    }
}
