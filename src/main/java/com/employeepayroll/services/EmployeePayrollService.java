package com.employeepayroll.services;

import java.util.List;


public class EmployeePayrollService {

    public enum IOServices
    {
        CONSOLE_ID, FILE_IO, DB_IO, REST_IO
    }

    private List<EmployeePayrollData> employeePayrollList;
    private EmployeePayrollService employeePayrollService;
    public EmployeePayrollService() {}


    public List<EmployeePayrollData> readEmployeePayrollData(IOServices ioService)
    {
        if(ioService.equals(IOServices.DB_IO))
            this.employeePayrollList = new EmployeePayrollDBService().readData();
        return this.employeePayrollList;
    }
}
