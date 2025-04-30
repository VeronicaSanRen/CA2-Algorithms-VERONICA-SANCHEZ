/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author dell
 */
public class ChiefDepartment {   // Chief Department class to storage each chief's details
    //Chief Nursing Officer
    //Chief Medical Officer
    //Chief Operating Officer
   
/**
 * Represents the chief of a department, managing a team of employees.
 */
    private Department department;
    private String chiefName;
    private List<Employee> team;

    public ChiefDepartment(Department department, String chiefName) {
        this.department = department;
        this.chiefName = chiefName;
        this.team = new ArrayList<>();
    }

    public Department getDepartment() {
        return department;
    }

    public String getChiefName() {
        return chiefName;
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
        return "Chief " + chiefName + " of " + department + " (Team size: " + team.size() + ")";
    }
}
