/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

        
/**
 *
 * @author dell
 */
public class CA2AlgorithmsVERONICASANCHEZ {

    /**
     * @param args the command line arguments
     */
        
       // Enum for menu options
         enum MenuOption {
        SORT, SEARCH, ADD_RECORD, GENERATE_RANDOM, EXIT
    }

    static List<Employee> employeeList = new ArrayList<>();
    static List<ChiefDepartment> chiefs = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        // Menu loop
        do {
            System.out.println("\n===== Organization Management Menu =====");
            System.out.println("1. Sort Employees");
            System.out.println("2. Search Employee");
            System.out.println("3. Add Employee Record");
            System.out.println("4. Generate Random Employees");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    recursiveSort(employeeList, 0, employeeList.size() - 1);
                    System.out.println("Sorted Employee Names (First 20):");
                    for (int i = 0; i < Math.min(20, employeeList.size()); i++) {
                        System.out.println((i+1) + ". " + employeeList.get(i).getName());
                    }
                    break;
                case 2:
                    System.out.print("Enter name to search: ");
                    String nameToSearch = scanner.nextLine();
                    int index = binarySearch(employeeList, nameToSearch, 0, employeeList.size() - 1);
                    if (index != -1) {
                        System.out.println("Found: " + employeeList.get(index));
                    } else {
                        System.out.println("Name not found.");
                    }
                    break;
                case 3:
                    addEmployee(scanner);
                    break;
                case 4:
                    generateRandomEmployees();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 5);
        scanner.close();
    }

    public static void recursiveSort(List<Employee> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            recursiveSort(list, left, mid);
            recursiveSort(list, mid + 1, right);
            merge(list, left, mid, right);
        }
    }

    private static void merge(List<Employee> list, int left, int mid, int right) {
        List<Employee> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Employee> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));
        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getName().compareToIgnoreCase(rightList.get(j).getName()) <= 0) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }
        while (i < leftList.size()) list.set(k++, leftList.get(i++));
        while (j < rightList.size()) list.set(k++, rightList.get(j++));
    }

    public static int binarySearch(List<Employee> list, String target, int left, int right) {
        if (left > right) return -1;
        int mid = (left + right) / 2;
        String midName = list.get(mid).getName();
        int cmp = midName.compareToIgnoreCase(target);
        if (cmp == 0) return mid;
        if (cmp < 0) return binarySearch(list, target, mid + 1, right);
        else return binarySearch(list, target, left, mid - 1);
    }

    public static void addEmployee(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter start date (YYYY MM DD): ");
        int year = scanner.nextInt(), month = scanner.nextInt(), day = scanner.nextInt();
        scanner.nextLine();
        Calendar startDate = Calendar.getInstance();
        startDate.set(year, month - 1, day);
        System.out.print("Enter department: ");
        Department dept;
        try { dept = Department.fromString(scanner.nextLine()); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); return; }
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        Employee emp = new Employee(name, startDate, dept, gender, email, salary);
        employeeList.add(emp);
        System.out.println("Added: " + emp);
    }

    public static void generateRandomEmployees() {
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona", "Danielle", "Phoebe", "Veronica", "Stephan", "Josh", "Mark", "Sophia", "Cameron", "Olivia", "Alice", "Tom", "David", "Mila", "Jessica"};
        String[] surname = {"Morales", "Sanchez", "Serrano", "Perez", "Garcia", "Lopez", "Campbell", "Tonkin", "Evans", "Johnson", "McQueen", "Pattinson", "Diaz", "Williams", "Jonas", "Stuart", "Morgan", "Russell", "Harris", "Davis"};
        String[] genders = {"Male", "Female", "Other"};
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            String name = names[rand.nextInt(names.length)] + " " + surname[rand.nextInt(surname.length)];
            Calendar startDate = Calendar.getInstance();
            startDate.set(2000 + rand.nextInt(24), rand.nextInt(12), 1 + rand.nextInt(28));
            Department dept = Department.values()[rand.nextInt(Department.values().length)];
            String gender = genders[rand.nextInt(genders.length)];
            String email = name.toLowerCase().replaceAll(" ", ".") + "@gmail.com";
            double salary = 40000 + rand.nextInt(60000);
            
            Employee emp = new Employee(name, startDate, dept, gender, email, salary);
            employeeList.add(emp);
            System.out.println("Generated: " + emp);
        }
       System.out.println(employeeList.toString());
       writeUsersToCSV(employeeList,"dummydata");
    }
    
    public static void writeUsersToCSV(List<Employee> users, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("Name,Start_Date,Department,Gender,Email,Salary\n");
            // Write each user's data
            for (Employee user : users) {
                writer.write(user.getName() + "," + user.getStartDate() + "," + user.getDepartment() + "," + user.getGender() + ","+ user.getEmail() +","+ user.getSalary() + "\n");
            }
            System.out.println("Data successfully written to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            
        }
    }

//    public static void loadDummyData(List<Employee> users, String filename) {
//
//        
//        try (Scanner scanner = new Scanner(new File(filename))) {
//    while (scanner.hasNextLine()) {
//        users.add(getRecordFromLine(scanner.nextLine()));
//    }
//}
//        // Assign chiefs
////        chiefs.add(new ChiefDepartment(Department.CARDIOLOGY, "Dr. Heart"));
////        chiefs.add(new ChiefDepartment(Department.RADIOLOGY, "Dr. X-Ray"));
//    }

//    private List<Employee> getRecordFromLine(String line) {
//    List<Employee> values = new ArrayList<Employee>();
//    try (Scanner rowScanner = new Scanner(line)) {
//        rowScanner.useDelimiter(",");
//        while (rowScanner.hasNext()) {
//            values.add(rowScanner.next());
//        }
//    }
//    return values;
//}
}
