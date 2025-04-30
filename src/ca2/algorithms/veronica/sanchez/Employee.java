/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;
import java.util.Calendar;
/**
 *
 * @author dell
 */
public class Employee {
    private String name;
    private Department department;
    private Calendar startDate;
 
    
    public Employee(String name, Calendar startDate, Department department) {
        this.name = name;
        this.startDate = startDate;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }
    
     public Calendar getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return name + " (Start Date: " + startDate.get(Calendar.DAY_OF_MONTH) + "/" 
               + (startDate.get(Calendar.MONTH) + 1) + "/" + startDate.get(Calendar.YEAR) + ", Dept: " 
               + department + ")";
    }
    
    

    
}
