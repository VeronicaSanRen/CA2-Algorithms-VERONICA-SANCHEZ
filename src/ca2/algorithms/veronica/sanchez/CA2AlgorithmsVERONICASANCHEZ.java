/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

        
/**
 *
 * @author dell
 */


/**
 * This class manages an organization system where we can manage employees, 
 * chiefs (managers), and departments. Operations include sorting, searching, 
 * adding records, generating random data, and saving data to CSV.
 */
public class CA2AlgorithmsVERONICASANCHEZ {

    /**
     * @param args the command line arguments
     */
        
       // Enum for menu options to control user operations
         enum MenuOption {
        SORT, SEARCH, ADD_RECORD, GENERATE_RANDOM, EXIT
    }

    // List to store employee and chief objects
    static List<Employee> employeeList = new ArrayList<>();
    static List<ChiefDepartment> chiefs = new ArrayList<>();

    /**
     * Main entry point of the program where user interacts with the menu*/
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(System.in);
        MenuOption choice;
        System.out.println("Would yo like to read a document : yes/no\n"); // Ask user if they want to load a document containing dummy data
        String input =scanner.nextLine();
        if (input.equalsIgnoreCase("yes")){
            
            loadDummyData("dummydata.csv"); // Load dummy data from CSV file
            System.out.println("Document read successfully! ");
         
        }
        // Menu loop for operations
        do {
            System.out.println("\n===== Organization Management Menu =====");
            System.out.println("1. Sort");
            System.out.println("2. Search");
            System.out.println("3. Add Record");
            System.out.println("4. Generate Random");
            System.out.println("5. Exit");
            System.out.print("Enter option (1-5): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline left by nextInt()
            
            // Determine menu choice based on user's selection
            choice = switch (option) {
                case 1 -> MenuOption.SORT;   // If user chooses 1, it's the "Sort" operation
                case 2 -> MenuOption.SEARCH;  // If user chooses 2, it's the "Search" operation
                case 3 -> MenuOption.ADD_RECORD;   // If user chooses 3, it's the "Add Record" operation
                case 4 -> MenuOption.GENERATE_RANDOM;   // If user chooses 4, it's the "Generate Random" operation
                case 5 -> MenuOption.EXIT;    // If user chooses 5, exit the program
                default -> {
                    System.out.println("Invalid choice, please try again.");
                    yield null;   // If user enters an invalid option, yield null and re-prompt
                }
            };
            // Process the user's choice if it's not exit
            if (choice != null && choice != MenuOption.EXIT) {
                handleEntity(choice.name(), scanner);  // Pass the selected operation (e.g., SORT) to handleEntity
            }
         } while (choice != MenuOption.EXIT);   // Repeat the menu until the user selects EXIT
        System.out.println("Exit successfully!");
        scanner.close();  // Close the scanner to avoid resource leaks
    }
    // ==  Unified entity handler for operations =====
    private static void handleEntity(String operation, Scanner scanner) {
        System.out.println("Please select (1-3): ");
        System.out.println("1. Employee");
        System.out.println("2. Chief/Manager");
        System.out.println("3. Department");
      
        int typeChoice = scanner.nextInt();
        scanner.nextLine();           // Consume newline
            
        // Switch between different entity operations based on user's choice
         switch (typeChoice) {
            case 1 -> handleEmployeeOperation(operation, scanner); // Call handleEmployeeOperation for "Employee"
            case 2 -> handleChiefOperation(operation, scanner);    // Call handleChiefOperation for "Chief" 
            case 3 -> handleDepartmentOperation(operation, scanner); // Call handleDepartmentOperation for "Department"
            default -> System.out.println("Invalid type selected.");
        }
    }
        
        
            // Handle operations related to employees
            private static void handleEmployeeOperation(String operation, Scanner scanner) {
                 switch (operation) {
                    case "SORT": {
                    recursiveSort(employeeList, 0, employeeList.size() - 1);  // Sort employees using recursive sort
                    System.out.println("Sorted Employees (First 20):");
                    for (int i = 0; i < Math.min(20, employeeList.size()); i++) {
                        System.out.println((i+1) + ". " + employeeList.get(i).getName());  // Print first 20 sorted employees
                    }
                    break;
                 }
                
                case "SEARCH": {
                    System.out.print("Enter employee name to search: ");
                    String name = scanner.nextLine();
                    int index = binarySearch(employeeList, name, 0, employeeList.size() - 1); // Perform binary search
                    System.out.println(index != -1 ? "Found: " + employeeList.get(index) : "Not found.");
                    break;
            }
                case "ADD_RECORD": {
                    addEmployee(scanner);  // Add new employee to the list
                    break;
                }
                case "GENERATE_RANDOM": {
                    generateRandomEmployees();  // Generate random employees
                    break;
                }
                default:  
                    System.out.println("Invalid operation.");
                    break;
        }
    }
                    
              // Handle operations related to chiefs (managers)      
          
        private static void handleChiefOperation(String operation, Scanner scanner) {
        switch (operation) {
                case "SORT": {
                    chiefs.sort(Comparator.comparing(ChiefDepartment::getChiefName)); // Sort chiefs by name
                    System.out.println("Sorted Managers (Chiefs): ");
                    chiefs.forEach(System.out::println);  // Print sorted chiefs
                    break;
                    }
                   
                case "SEARCH": {
                    System.out.print("Enter manager name to search: ");
                    String name = scanner.nextLine();
                    boolean found = false;
                    for (ChiefDepartment chief : chiefs) {
                        if (chief.getChiefName().equalsIgnoreCase(name)) {  // Search by chief name
                            System.out.println("Found: " + chief);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Manager not found.");
                    }
                    break;
                }  
                case "ADD_RECORD": {    // Add new chief to the list
                    System.out.print("Enter manager name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter department: ");
                    Department dept;
                    try {
                       dept = Department.fromString(scanner.nextLine());
                       
                       System.out.print("Enter start date (YYYY MM DD): ");
                       int year = scanner.nextInt(), month = scanner.nextInt(), day = scanner.nextInt();
                       scanner.nextLine();
                       Calendar startDate = Calendar.getInstance();
                       startDate.set(year, month - 1, day);

                       System.out.print("Enter gender: ");
                       String gender = scanner.nextLine();
  
                       System.out.print("Enter email: ");
                       String email = scanner.nextLine();

                       System.out.print("Enter salary: ");
                       double salary = scanner.nextDouble();
                       scanner.nextLine();
                       
                       
                       chiefs.add(new ChiefDepartment(dept, name, startDate, gender, email, salary));
                       System.out.println("Manager added.");
                   } catch (IllegalArgumentException e) {
                       System.out.println(e.getMessage());
                }
                break;
            }   
                 
                case "GENERATE_RANDOM": {
                 generateRandomChiefs(); // Generate random chiefs
                 break;
            }
                default:
                System.out.println("Invalid operation.");
                break;
        }
    }
            
        
          // Handle operations related to departments
        private static void handleDepartmentOperation(String operation, Scanner scanner) {
            switch (operation) {
                case "SORT": {
                   Department[] departments = Department.values();  // Get all department enum values
                   Arrays.sort(departments, Comparator.comparing(Enum::name));  // Sort departments alphabetically
                   for (Department d : departments) {
                    System.out.println("- " + d);  // Print sorted departments
                }
                    break;
            }
                case "SEARCH": {
                    System.out.print("Enter department name: ");
                    String deptName = scanner.nextLine();
                    try {
                        Department department = Department.fromString(deptName);  // Search for department by name
                        System.out.println("Department exists: " + department);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Department not found.");
                   }
                    break;
                }
                  
                case "ADD_RECORD", "GENERATE_RANDOM": {
                    System.out.println("Departments are predefined.");  // No need to add or generate departments manually
                    break;
            }
               default: 
                    System.out.println("Unsupported operation.");
                    break;
        }
    }
    // Recursive merge sort algorithm to sort employees by name
    public static void recursiveSort(List<Employee> list, int left, int right) {
        if (left < right) {  // Base case: when left index is greater than right, stop
            int mid = (left + right) / 2;  // Find the middle index to divide the list
            recursiveSort(list, left, mid);   // Recursively sort the left half
            recursiveSort(list, mid + 1, right);    // Recursively sort the right half
            merge(list, left, mid, right);  // Merge the sorted halves back together
        }
    }
    // Merge the sorted left and right halves of the employee list
    private static void merge(List<Employee> list, int left, int mid, int right) {
        List<Employee> leftList = new ArrayList<>(list.subList(left, mid + 1));  // Create left sublist
        List<Employee> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));  // Create right sublist
        int i = 0, j = 0, k = left;  // Initialize pointers for left, right, and merged list
        
        // Merge the left and right lists based on lexicographical order of names
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getName().compareToIgnoreCase(rightList.get(j).getName()) <= 0) {
                list.set(k++, leftList.get(i++)); // If left name is smaller or equal, add to the merged list
            } else {
                list.set(k++, rightList.get(j++));  // Otherwise, add the right name to the merged list
            }
        }
        
        // Copy any remaining elements from the left list into the merged list
        while (i < leftList.size()) list.set(k++, leftList.get(i++));
        // Copy any remaining elements from the right list into the merged list
        while (j < rightList.size()) list.set(k++, rightList.get(j++));
    }
    // Binary search algorithm to find an employee by name in the sorted list
    public static int binarySearch(List<Employee> list, String target, int left, int right) {
        
        if (left > right) return -1;  // Base case: if the range is invalid, return -1 (not found)
        int mid = (left + right) / 2;  // Find the middle index of the current range
        String midName = list.get(mid).getName();  // Get the name at the middle index
        int cmp = midName.compareToIgnoreCase(target);  // Compare the middle name with the target name
        if (cmp == 0) return mid;  // If they are equal, return the middle index (found)
        if (cmp < 0) return binarySearch(list, target, mid + 1, right);  // If target is greater, search right half
        else return binarySearch(list, target, left, mid - 1);  // If target is smaller, search left half
    }
    // Add a new employee record to the list
    public static void addEmployee(Scanner scanner) {
        System.out.print("Enter name: ");  // Prompt user to enter employee name
        String name = scanner.nextLine();
        System.out.print("Enter start date (YYYY MM DD): ");  // Prompt user to enter start date (year, month, day)
        int year = scanner.nextInt(), month = scanner.nextInt(), day = scanner.nextInt();
        scanner.nextLine();
        Calendar startDate = Calendar.getInstance();  // Create a calendar object with the given start date
        startDate.set(year, month - 1, day);  // Month is 0-based in Calendar
        System.out.print("Enter department: ");  // Prompt user to enter department name
        Department dept;
        try { 
            dept = Department.fromString(scanner.nextLine());  // Try to convert the string to a Department enum
        }
        catch (IllegalArgumentException e) { // Handle invalid department input
            System.out.println(e.getMessage()); 
            return; 
        }
        System.out.print("Enter gender: "); // Prompt for gender
        String gender = scanner.nextLine();
        System.out.print("Enter email: ");  // Prompt for email
        String email = scanner.nextLine();
        System.out.print("Enter salary: ");   // Prompt for salary
        double salary = scanner.nextDouble();
        scanner.nextLine();
        // Create the Employee object and add to the list
        Employee emp = new Employee(name, startDate, dept, gender, email, salary);
        employeeList.add(emp);
        System.out.println("Added: " + emp);  // Confirm addition
    }
    
    // Generate random employees and add them to the list
    public static void generateRandomEmployees() {
        // Arrays for random data generation
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona", "Danielle", "Phoebe", "Veronica", "Stephan", "Josh", "Mark", "Sophia", "Cameron", "Olivia", "Alice", "Tom", "David", "Mila", "Jessica"};
        String[] surname = {"Morales", "Sanchez", "Serrano", "Perez", "Garcia", "Lopez", "Campbell", "Tonkin", "Evans", "Johnson", "McQueen", "Pattinson", "Diaz", "Williams", "Jonas", "Stuart", "Morgan", "Russell", "Harris", "Davis"};
        String[] genders = {"Male", "Female", "Other"};
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {  // Generate 20 random employees
            String name = names[rand.nextInt(names.length)] + " " + surname[rand.nextInt(surname.length)];
            Calendar startDate = Calendar.getInstance();  // Create a random start date between year 2000 and 2023
            startDate.set(2000 + rand.nextInt(24), rand.nextInt(12), 1 + rand.nextInt(28));
            Department dept = Department.values()[rand.nextInt(Department.values().length)];  // Random department from enum
            String gender = genders[rand.nextInt(genders.length)];   // Random gender
            String email = name.toLowerCase().replaceAll(" ", ".") + "@gmail.com";   // Generate email using name
            double salary = 40000 + rand.nextInt(60000);   // Random salary between 40,000 and 100,000
            
            // Create and add employee
            Employee emp = new Employee(name, startDate, dept, gender, email, salary);
            employeeList.add(emp);
            System.out.println("Generated: " + emp);
        }
      
       // Save generated employees to CSV
       writeUsersToCSV(employeeList,"dummydata.csv");
    }
    
    // Generate random chiefs (managers) and add them to the list
    public static void generateRandomChiefs() {
      // Arrays for name generation
      String[] names = {"Lisa", "Michael", "Angela", "Robert", "Karen", "Steven", "Nina", "George", "Marissa", "David", "Summer", "Riley", "Alejandro", "Albert", "Rick", "Veronica", "James", "Mark", "Andrea", "Christine"};
      String[] surnames = {"Smith", "Johnson", "Brown", "Taylor", "Clark", "Walker", "Hall", "Allen", "Young", "King", "Tonkin", "Campbell", "Diaz", "Williams", "Duran", "Rudd", "Jordan", "Harris", "Davis", "Stuart"};
      String[] genders = {"Male", "Female", "Other"};
      Random rand = new Random();

        // Generate 20 chiefs
        for (int i = 0; i < 20; i++) {
        
       
            String name = names[rand.nextInt(names.length)] + " " + surnames[rand.nextInt(surnames.length)];
            Calendar startDate = Calendar.getInstance(); // Random start date
            startDate.set(2000 + rand.nextInt(24), rand.nextInt(12), 1 + rand.nextInt(28));
            Department dept = Department.values()[rand.nextInt(Department.values().length)];
            String gender = genders[rand.nextInt(genders.length)];
            String email = name.toLowerCase().replaceAll(" ", ".") + "@gmail.com";
            double salary = 40000 + rand.nextInt(60000);
        ChiefDepartment chief = new ChiefDepartment(dept, name, startDate, gender, email, salary);  // Create and add chief
        chiefs.add(chief);
        System.out.println("Generated: " + chief);
    }

        // Save generated chiefs to CSV
         writeChiefsToCSV(chiefs, "chiefs_dummydata.csv");
}
   
    
    // Write employee data to CSV file
    public static void writeUsersToCSV(List<Employee> users, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write CSV header
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
    
    // Write chief data to CSV file
    public static void writeChiefsToCSV(List<ChiefDepartment> chiefs, String filename) {
    try (FileWriter writer = new FileWriter(filename)) {
        writer.write("Name,Start_Date,Department,Gender,Email,Salary\n");  // Write CSV header
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ChiefDepartment chief : chiefs) {    // Write each chief's data
            writer.write(chief.getChiefName() + "," +
                         sdf.format(chief.getStartDate().getTime()) + "," +
                         chief.getDepartment() + "," +
                         chief.getGender() + "," +
                         chief.getEmail() + "," +
                         chief.getSalary() + "\n");
        }
        System.out.println("Chiefs data successfully written to " + filename);
    } catch (IOException e) {
        System.out.println("Error writing chiefs to file.");
    }
  
  }
    // Load dummy data from CSV into employee list
    public static void loadDummyData(String filename) throws FileNotFoundException, IOException, ParseException {
    
    // Create a BufferedReader to read the file line by line
    BufferedReader br = new BufferedReader(new FileReader(filename));
    String line;
    int flag=0;   // Used to skip the header row
    while ((line = br.readLine()) != null) {   // Read each line in the file
        String[] values = line.split(",");   // Split the line into fields using comma as delimiter
        flag=flag+1;
        if (flag>1)  { // Skip header row
            // Extract values from the row
            String name = values[0];  // Employee name
            Calendar startDate = Calendar.getInstance();   // Create a Calendar object to store start date
            String dateString = values[1].trim(); // Parse date string into Calendar

        // Define expected date format in the CSV file
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date date = sdf.parse(dateString);  // Parse the string into a Date object, then set it in the Calendar
        startDate.setTime(date);
        String input = values[2];  // Convert department string to Department enum
        Department department = Department.fromString(input);
        // Read gender and email
        String gender = values[3];
        String email = values[4];
        double salary = Double.parseDouble(values[5]);    // Parse salary from string to double
        
         // Create and add employee
        Employee emp = new Employee(name, startDate, department, gender, email, salary);
        employeeList.add(emp);   // Add employee to the global list
         }
    }
        // Display all loaded employees
        for(int j=0; j<employeeList.size();j++){
        System.out.println(employeeList.get(j).toString());
    }
    
    }    
}

