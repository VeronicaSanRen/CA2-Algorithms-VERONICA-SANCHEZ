/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca2.algorithms.veronica.sanchez;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
        MenuOption choice;
        
        // Menu loop
        do {
            System.out.println("\n===== Organization Management Menu =====");
            System.out.println("1. Sort");
            System.out.println("2. Search");
            System.out.println("3. Add Record");
            System.out.println("4. Generate Random");
            System.out.println("5. Exit");
            System.out.print("Enter option (1-5): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            choice = switch (option) {
                case 1 -> MenuOption.SORT;
                case 2 -> MenuOption.SEARCH;
                case 3 -> MenuOption.ADD_RECORD;
                case 4 -> MenuOption.GENERATE_RANDOM;
                case 5 -> MenuOption.EXIT;
                default -> {
                    System.out.println("Invalid choice, please try again.");
                    yield null;
                }
            };

            if (choice != null && choice != MenuOption.EXIT) {
                handleEntity(choice.name(), scanner); 
            }
         } while (choice != MenuOption.EXIT);
        System.out.println("Exit successfully!");
        scanner.close();
    }
    // ==  Unified entity handler for operations =====
    private static void handleEntity(String operation, Scanner scanner) {
        System.out.println("Please select (1-3): ");
        System.out.println("1. Employee");
        System.out.println("2. Chief/Manager");
        System.out.println("3. Department");
      
        int typeChoice = scanner.nextInt();
        scanner.nextLine();               // 
            
        
         switch (typeChoice) {
            case 1 -> handleEmployeeOperation(operation, scanner); // 
            case 2 -> handleChiefOperation(operation, scanner);    // 
            case 3 -> handleDepartmentOperation(operation, scanner); // 
            default -> System.out.println("Invalid type selected.");
        }
    }
        
        
            // Employee
            private static void handleEmployeeOperation(String operation, Scanner scanner) {
                 switch (operation) {
                    case "SORT": {
                    recursiveSort(employeeList, 0, employeeList.size() - 1);
                    System.out.println("Sorted Employees (First 20):");
                    for (int i = 0; i < Math.min(20, employeeList.size()); i++) {
                        System.out.println((i+1) + ". " + employeeList.get(i).getName());
                    }
                    break;
                 }
                
                case "SEARCH": {
                    System.out.print("Enter employee name to search: ");
                    String name = scanner.nextLine();
                    int index = binarySearch(employeeList, name, 0, employeeList.size() - 1);
                    System.out.println(index != -1 ? "Found: " + employeeList.get(index) : "Not found.");
                    break;
            }
                case "ADD_RECORD": {
                    addEmployee(scanner);
                    break;
                }
                case "GENERATE_RANDOM": {
                    generateRandomEmployees();
                    break;
                }
                default:  
                    System.out.println("Invalid operation.");
                    break;
        }
    }
                    
              // Chief/Manager          
          
        private static void handleChiefOperation(String operation, Scanner scanner) {
        switch (operation) {
                case "SORT": {
                    chiefs.sort(Comparator.comparing(ChiefDepartment::getChiefName));
                    System.out.println("Sorted Managers (Chiefs): ");
                    chiefs.forEach(System.out::println);
                    break;
                    }
                   
                case "SEARCH": {
                    System.out.print("Enter manager name to search: ");
                    String name = scanner.nextLine();
                    boolean found = false;
                    for (ChiefDepartment chief : chiefs) {
                        if (chief.getChiefName().equalsIgnoreCase(name)) {
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
                case "ADD_RECORD": {
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
                 generateRandomChiefs();
                 break;
            }
                default:
                System.out.println("Invalid operation.");
                break;
        }
    }
            
        
         // Department
        private static void handleDepartmentOperation(String operation, Scanner scanner) {
            switch (operation) {
                case "SORT": {
                   Department[] departments = Department.values();
                   Arrays.sort(departments, Comparator.comparing(Enum::name));
                   for (Department d : departments) {
                    System.out.println("- " + d);
                }
                    break;
            }
                case "SEARCH": {
                    System.out.print("Enter department name: ");
                    String deptName = scanner.nextLine();
                    try {
                        Department department = Department.fromString(deptName);
                        System.out.println("Department exists: " + department);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Department not found.");
                   }
                    break;
                }
                  
                case "ADD_RECORD", "GENERATE_RANDOM": {
                    System.out.println("Departments are predefined.");
                    break;
            }
               default: 
                    System.out.println("Unsupported operation.");
                    break;
        }
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
       writeUsersToCSV(employeeList,"dummydata.csv");
    }
    
    public static void generateRandomChiefs() {
      String[] names = {"Lisa", "Michael", "Angela", "Robert", "Karen", "Steven", "Nina", "George", "Marissa", "David", "Summer", "Riley", "Alejandro", "Albert", "Rick", "Veronica", "James", "Mark", "Andrea", "Christine"};
      String[] surnames = {"Smith", "Johnson", "Brown", "Taylor", "Clark", "Walker", "Hall", "Allen", "Young", "King", "Tonkin", "Campbell", "Diaz", "Williams", "Duran", "Rudd", "Jordan", "Harris", "Davis", "Stuart"};
      String[] genders = {"Male", "Female", "Other"};
      Department[] departments = Department.values();
      Random rand = new Random();

      Set<Department> assignedDepartments = new HashSet<>();
        for (int i = 0; i < 20; i++) {
        String name = names[rand.nextInt(names.length)] + " " + surnames[rand.nextInt(surnames.length)];
       
        Department dept;
         do {
            dept = Department.values()[rand.nextInt(Department.values().length)];
        } while (assignedDepartments.contains(dept));
         
        assignedDepartments.add(dept);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2000 + rand.nextInt(24), rand.nextInt(12), 1 + rand.nextInt(28));

        String gender = genders[rand.nextInt(genders.length)];
        String email = name.toLowerCase().replaceAll(" ", ".") + "@gmail.com";
        double salary = 70000 + rand.nextInt(50000);
        
        ChiefDepartment chief = new ChiefDepartment(dept, name, startDate, gender, email, salary);
        chiefs.add(chief);
        System.out.println("Generated: " + chief);
    }

      // Write to CSV
         writeChiefsToCSV(chiefs, "chiefs_dummydata.csv");
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
    
    
    public static void writeChiefsToCSV(List<ChiefDepartment> chiefs, String filename) {
    try (FileWriter writer = new FileWriter(filename)) {
        writer.write("Name,Start_Date,Department,Gender,Email,Salary\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ChiefDepartment chief : chiefs) {
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
