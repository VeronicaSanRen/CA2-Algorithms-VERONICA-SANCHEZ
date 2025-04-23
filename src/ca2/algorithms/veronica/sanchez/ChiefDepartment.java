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
public class ChiefDepartment {   // Chief Department class to storage each chief's details
    //Chief Nursing Officer
    //Chief Medical Officer
    //Chief Operating Officer
   
    private String name;
    private String department;
    private Calendar startDate;
    
    
    ChiefDeparmtent (String name, String department, Calendar startDate) {
            this.name = name;
            this.department = department;
            this.startDate = startDate;
        }
    
     public String toString() {
            return name + " " + department + " " + startDate;
        }
}
