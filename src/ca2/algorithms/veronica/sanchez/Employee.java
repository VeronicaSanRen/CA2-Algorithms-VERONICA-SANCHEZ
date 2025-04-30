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
    private String gender;
    private String email;
    private double salary;
 
    
    public Employee(String name, Calendar startDate, Department department, String gender, String email, double salary) {
        this.name = name;
        this.startDate = startDate;
        this.department = department;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
       
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
     
    public String getGender() { 
        return gender; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public double getSalary() { 
        return salary; 
    }

       @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %.2f",
                name,
                new java.text.SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime()),
                department,
                gender,
                email,
                salary);
    }
    
    
    
//    @Override
//    public String toString() {
//        return name + " (Start Date: " + startDate.get(Calendar.DAY_OF_MONTH) + "/" 
//               + (startDate.get(Calendar.MONTH) + 1) + "/" + startDate.get(Calendar.YEAR) + ", Dept: " 
//               + department + ")";
    }
    
    

    

