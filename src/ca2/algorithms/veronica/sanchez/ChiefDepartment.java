/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
/**
 *
 * @author dell
 */
public class ChiefDepartment {   // Chief Department class to storage each chief's details
    
/**
 * Represents the chief of a department, managing a team of employees.
 */
    private Department department;
    private String chiefName;
    private Calendar startDate;
    private String gender;
    private String email;
    private double salary;
    private List<Employee> team;

    public ChiefDepartment(Department department, String chiefName, Calendar startDate, String gender, String email, double salary) {
        this.department = department;
        this.chiefName = chiefName;
        this.startDate = startDate;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.team = new ArrayList<>();
    }

    public Department getDepartment() {
        return department;
    }

    public String getChiefName() {
        return chiefName;
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
    public List<Employee> getTeam() {
        return team;
    }

    public void addTeamMember(Employee e) {
        if (e.getDepartment() == this.department) {
            team.add(e);
        } else {
            throw new IllegalArgumentException("Employee department does not match chief's department.");
        }
    }

    @Override
    public String toString() {
        return "Chief " + chiefName + " of " + department + " | Start Date: " +
                startDate.get(Calendar.YEAR) + "-" + (startDate.get(Calendar.MONTH) + 1) + "-" + startDate.get(Calendar.DAY_OF_MONTH) +
                ", Gender: " + gender + ", Email: " + email + ", Salary: " + salary;
    }
}
