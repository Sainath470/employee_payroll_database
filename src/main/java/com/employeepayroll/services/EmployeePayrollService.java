package com.employeepayroll.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class EmployeePayrollService {

    private List<EmployeePayrollData> employeePayrollList;
    private EmployeePayrollDBService employeePayrollDBService;

    /**
     * created ioService enum class for CONSOLE_IO, FILE_IO,DB_IO
     */
    public enum IOServices {CONSOLE_ID, DB_IO, FILE_IO, REST_IO}

    //created default constructor for singleton class
    public EmployeePayrollService()
    {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    /**
     * created parameterized constructor
     * @param employeePayrollList employeePayrollList
     */
    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList){
        this();
        this.employeePayrollList = employeePayrollList;
    }


    /**
     * created readEmployeePayrollData method to read data from ioService of database Io
     * @param ioService ioService
     * @return this.employeePayrollList
     */
    public List<EmployeePayrollData> readEmployeePayrollData(IOServices ioService)
    {
        if(ioService.equals(IOServices.DB_IO))
            this.employeePayrollList = new EmployeePayrollDBService().readData();
        return this.employeePayrollList;
    }

    /**
     * created checkEmployeePayrollSyncWithDB method to check whether updated data is synced with database or not
     * @param name name
     * @return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name))
     */
    public boolean checkEmployeePayrollInSyncWithDB(String name)
    {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.equals(employeePayrollDBService.getEmployeePayrollData(name));
    }

    public List<EmployeePayrollData> readEmployeePayrollDataForDateRange(IOServices ioService, LocalDate startDate, LocalDate endDate)
    {
        if (ioService.equals(IOServices.DB_IO))
            return employeePayrollDBService.getEmployeePayrollDataForDateRange(startDate, endDate);
        return null;
    }

    public void updateEmployeeSalary(String name, Double salary){
        int result = employeePayrollDBService.updateEmployeePayrollData(name,salary);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData;
        employeePayrollData= this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.salary = salary;
    }

    public void updateEmployeeBasicPay(String name, Double basic_pay) {
        int result = employeePayrollDBService.updateEmployeePayrollDataForBasicPay(name, basic_pay);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData;
        employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.basicPay = basic_pay;
    }

    private EmployeePayrollData getEmployeePayrollData(String name){
        return this.employeePayrollList.stream().filter(employeePayrollData ->
                employeePayrollData.name.equals(name)).findFirst().orElse(null);
    }


    public List<EmployeePayrollData> readEmployeePayrollDataForDataRange( LocalDate startDate, LocalDate endDate){
        return employeePayrollDBService.getEmployeePayrollDataForDateRange(startDate,endDate);
    }

    public Map<String, Double> getAverageSalaryGroupByGender(){
        return employeePayrollDBService.readAverageSalaryGroupByGender();
    }

    public Map<String, Double> getMinimumSalaryGroupByGender(){
        return employeePayrollDBService.readMinimumSalaryGroupByGender();
    }

    public Map<String, Double> getMaximumSalaryGroupByGender(){
        return employeePayrollDBService.readMaximumSalaryGroupByGender();
    }

    public Map<String, Double> getCountOfEmployeeGroupByGender(){
        return employeePayrollDBService.readCountOfEmployeesGroupByGender();
    }

    public void addEmployeeToPayroll(String name, double basic_pay, LocalDate start, String gender){
        employeePayrollList.add(employeePayrollDBService.addEmployeePayroll(name, basic_pay, start,gender));
    }
}
