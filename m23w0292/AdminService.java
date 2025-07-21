package twentyfour.fall.oop.group1.finalproject.m23w0292;

import java.io.*;
import java.util.*;


public class AdminService {
    private ArrayList<Store> stores; // List to hold all store objects
    private HashMap<String, Store> storesByName; // Map for quick lookup of stores by name
    private Scanner scanner; // Scanner for user input
    private HashMap<String, Employee> allEmployees; // Map to store all employees globally by their unique ID
    private HashSet<String> allEmployeeIds; // Set to track all employee IDs for uniqueness
    private IceCreamShop iceCreamShop; // Instance of IceCreamShop to manage store-specific operations

      // Initialize the collections and objects
    public AdminService() {
        stores = new ArrayList<>();
        storesByName = new HashMap<>();
        scanner = new Scanner(System.in);
        allEmployees = new HashMap<>(); 
        allEmployeeIds = new HashSet<>();
        this.iceCreamShop = new IceCreamShop();
    }
    
    /**
     * Admin login method.
     * Validates hardcoded username and password for demonstration purposes.
     */

    public boolean login(String username, String password) {
        
        return username.equals("Flora") && password.equals("flora123#");
    }

  /**
     * Allows management of employees for a specific store.
     * The user can add, remove, view employees or view all employees.
     */

       public void manageEmployees() {
        System.out.print("Enter store name to manage employees: ");
        String storeName = scanner.nextLine().trim();
        Store store = findStore(storeName);

        if (store != null) {
            while (true) {
                System.out.println("Employee Management for " + storeName);
                System.out.println("1. Add Employee");
                System.out.println("2. Remove Employee");
                System.out.println("3. View Employees");
                System.out.println("4. View All Employees"); 
                System.out.println("5. Back to Admin Menu");
                System.out.print("Choose an option: ");
                int choice = getUserChoice();

                switch (choice) {
                    case 1 -> addEmployee(store);
                    case 2 -> removeEmployee(store);
                    case 3 -> viewEmployees(store);
                    case 4 -> viewAllEmployees();
                    case 5 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Store not found.");
        }
    }
       
        /**
     * Adds a new employee to a store.
     * @param store
     */

    public void addEmployee(Store store) {
        System.out.print("Enter employee name: ");
        String employeeName = scanner.nextLine().trim();
        System.out.print("Enter employee role: ");
        String role = scanner.nextLine().trim();

      // Generate a unique employee ID
        String employeeID = generateUniqueEmployeeID(); 
        Employee employee = new Employee(employeeName, role, employeeID);
        
        // Add employee to the store and global employee list
        store.addEmployee(employee);
        allEmployees.put(employeeID, employee); 
    }
    
    
    /**
     * Generates a unique employee ID.
     */
    private String generateUniqueEmployeeID() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        while (allEmployeeIds.contains(id)) { 
            id = UUID.randomUUID().toString().substring(0, 8);
        }
        allEmployeeIds.add(id); // Add the generated ID to the HashSet
        return id;
    }

    
    /**
     * Removes an employee from a store.
     * @param store
     */
    public void removeEmployee(Store store) {
        System.out.print("Enter employee ID to remove: ");
        String employeeID = scanner.nextLine().trim();

        if (allEmployees.containsKey(employeeID)) {
             // Remove the employee from the specific store and global list
            store.getEmployees().removeIf(emp -> emp.getEmployeeID().equals(employeeID)); 
            allEmployees.remove(employeeID); 
            System.out.println("Employee removed successfully.");
        } else {
            System.out.println("Employee ID not found.");
        }
    }
   
    /**
     * Displays all employees for a specific store.
     * @param store
     */
    public void viewEmployees(Store store) {
        if (store.getEmployees().isEmpty()) {
            System.out.println("No employees found for this store.");
        } else {
            System.out.println("Employees for " + store.getName() + ":");
            for (Employee employee : store.getEmployees()) {
                System.out.println("Employee ID: " + employee.getEmployeeID() + 
                                   ", Name: " + employee.getName() + 
                                   ", Role: " + employee.getRole());
            }
        }
    }
    
    /**
     * Displays all employees across all stores.
     */

    public void viewAllEmployees() {
        if (allEmployees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("All Employees:");
            for (Employee employee : allEmployees.values()) {
                System.out.println("Employee ID: " + employee.getEmployeeID() + 
                                   ", Name: " + employee.getName() + 
                                   ", Role: " + employee.getRole());
            }
        }
    }
   
    /**
     * Finds an employee by their ID.
     * @param employeeID
     * @return 
     */
    public Employee findEmployee(String employeeID) {
        return allEmployees.getOrDefault(employeeID, null);
    }


    public void manageStores() {
        System.out.print("Enter new store name: ");
        String storeName = scanner.nextLine().trim(); 

        addStore(storeName); 
    }
  
     /**
     * Adds a new store to the system.
     * @param name
     */
     public void addStore(String name) {
        if (storesByName.containsKey(name)) {
            System.out.println("Store with this name already exists.");
        } else {
            Store store = new Store(name, iceCreamShop); 
            stores.add(store);
            storesByName.put(name, store); 
            System.out.println("Store " + name + " added successfully.");
        }
     }
    public void manageInventory() {
        System.out.print("Enter store name to manage inventory: ");
        String storeName = scanner.nextLine().trim();
        Store store = findStore(storeName);

        if (store != null) {
            while (true) {
                System.out.println("Inventory Management for " + storeName);
                System.out.println("1. Add Item");
                System.out.println("2. Remove Item");
                System.out.println("3. View Inventory");
                System.out.println("4. Back to Admin Menu");
                System.out.print("Choose an option: ");
                int choice = getUserChoice();

                switch (choice) {
                    case 1 -> addItemToInventory(store);
                    case 2 -> removeItemFromInventory(store);
                    case 3 -> store.displayInventory(); // Call displayInventory() method of the Store object
                    case 4 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Store not found.");
        }
    }

    private void addItemToInventory(Store store) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine().trim(); 
        System.out.print("Enter quantity: ");
        int quantity = getValidQuantity(); 

        store.addItem(itemName, quantity); 
    }

    private void removeItemFromInventory(Store store) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine().trim(); 
        System.out.print("Enter quantity: ");
        int quantity = getValidQuantity(); 

        store.removeItem(itemName, quantity); 
    }

    /**
     * Finds a store by its name.
     * @param name
     * @return 
     */
    public Store findStore(String name) {
        return storesByName.getOrDefault(name, null); 
    }

  
  
    public void viewReports() {
    System.out.println("Reports:");
    boolean hasReports = false;

    if (stores.isEmpty()) { 
        System.out.println("No stores found."); 
    } else { 
        for (Store store : stores) {
            if (store != null) { 
                if (!store.getInventory().isEmpty() || !store.getOrders().isEmpty()) { 
                    System.out.println("Store: " + store.getName());

                    // Display Inventory (if available)
                    if (!store.getInventory().isEmpty()) { 
                        store.displayInventory(); 
                    } else {
                        System.out.println("Inventory is empty."); 
                    }

                    System.out.println("Orders: " + store.getOrders().size());

                    // Calculate and display employee sales and total sales
                    if (!store.getOrders().isEmpty()) { // Check if there are any orders
                        Map<String, Double> employeeSales = new HashMap<>(); 

                        for (Order order : store.getOrders()) {
                            String employeeID = order.getEmployeeID();
                            double orderTotal = order.getTotalPrice();
                            employeeSales.put(employeeID, employeeSales.getOrDefault(employeeID, 0.0) + orderTotal); 

                            // Display Order Details with Date/Time
                            System.out.println("  Order ID: " + order.getOrderID());
                            System.out.println("  Order Date/Time: " + order.getFormattedDateTime()); // Use the formatted date/time
                            System.out.println("  Customer: " + order.getCustomerName());
                            // Display order items and their prices (optional)
                            for (OrderItem item : order.getOrderItems()) {
                                System.out.println("    " + item.getItemName() + " - " + item.getQuantity() + " x $" + item.getPrice() + " = $" + item.getTotalPrice());
                            }
                            System.out.println("  Total: $" + orderTotal); 
                            System.out.println("  Employee: " + findEmployee(employeeID).getName()); 
                            System.out.println("------------------"); 
                        }

                        System.out.println("Employee Sales Summary:");
                        for (Map.Entry<String, Double> entry : employeeSales.entrySet()) {
                            Employee employee = findEmployee(entry.getKey()); 
                            if (employee != null) {
                                System.out.println("  " + employee.getName() + ": $" + String.format("%.2f", entry.getValue()));
                            }
                        }

                        double totalSales = employeeSales.values().stream().mapToDouble(Double::doubleValue).sum(); 
                        System.out.println("Total Sales: $" + String.format("%.2f", totalSales)); 
                    } else {
                        System.out.println("No orders placed."); 
                    }

                    System.out.println("----------------------"); 
                    hasReports = true; 
                }
            }
        }

        if (!hasReports) { 
            System.out.println("No reports available."); 
        }
    }
}
   
    public void saveStores() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("stores.txt"))) {
        for (Store store : stores) {
            if (store != null) { // Check for null store before writing
                writer.write(store.getName());
                writer.newLine();

                // Save employees (if applicable)
                for (Employee employee : store.getEmployees()) { 
                    writer.write("Employee:" + employee.getName() + "," + employee.getRole());
                    writer.newLine();
                }

                // Save inventory
                for (Map.Entry<String, Integer> entry : store.getInventory().entrySet()) {
                    writer.write("Inventory:" + entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }

                writer.write("END"); // End of store data
                writer.newLine();
            }
        }
    } catch (IOException e) {
        System.err.println("Error saving store data: " + e.getMessage()); 
    }
}
    public void loadStores() throws IOException {
    File file = new File("stores.txt");
    if (!file.exists()) {
        System.out.println("No store data found. Starting fresh."); 
        return; 
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("END")) {
                continue; // End of store data
            }

            // Create a new Store object with the store name 
            Store store = new Store(line, iceCreamShop); 
            stores.add(store);
            storesByName.put(line, store); 

            while ((line = reader.readLine()) != null && !line.equals("END")) {
                if (line.startsWith("Employee:")) {
                    // Handle employee loading (if applicable) 
                    // ... 
                } else if (line.startsWith("Inventory:")) {
                    String[] parts = line.split(":")[1].split(",");
                    store.addItem(parts[0], Integer.parseInt(parts[1]));
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error loading stores from file: " + e.getMessage()); 
    }
}

    private int getUserChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private int getValidQuantity() {
        while (true) {
            try {
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (quantity > 0) {
                    return quantity;
                } else {
                    System.out.println("Quantity must be greater than zero. Please enter again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }
    // Add a getter method for the 'stores' list
    public ArrayList<Store> getStores() {
        return stores;
    }
}
