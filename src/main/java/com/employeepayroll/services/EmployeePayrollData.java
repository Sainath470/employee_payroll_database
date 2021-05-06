package com.employeepayroll.services;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {
    //declaring variables
    public int id;
    public String name;
    public double salary;
    public double basicPay;
    public LocalDate startDate;

    /**
     * created a parameterized constructor
     * @param id id
     * @param name name
     * @param salary salary
     * @param basicPay basicPay
     */
    public EmployeePayrollData(int id, String name, double salary, double basicPay, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.basicPay = basicPay;
        this.startDate = startDate;
    }

    //overriding toString method
    @Override
    public String toString()
    {
        return "id=" + id +
                ", salary=" + salary +
                ", name='" + name + '\'' +
                ", Basic Pay='" + basicPay + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    //Overiding equals method
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name);
    }
}
