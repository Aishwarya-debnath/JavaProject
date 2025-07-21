package twentyfour.fall.oop.group1.finalproject.m23w0292;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main class for the Ice Cream Shop Franchise Management System.
 * Handles the core functionalities such as admin and employee login,
 * menu navigation, and invoking services like store management, 
 * order processing, and reporting.
 */

public class FranchiseManagementSystem {

    private static AdminService adminService = new AdminService();

    private static Scanner scanner = new Scanner(System.in);

    private static Employee currentlyLoggedInEmployee;


    public static void main(String[] args) {
        

        try {

            adminService.loadStores(); // Load stores from file at startup

        } catch (IOException e) {

            System.out.println("Error loading stores: " + e.getMessage());

        }

// Main menu loop
        OUTER:

        while (true) {

            System.out.println("Welcome to the Ice Cream Shop Franchise Management System");

            String[] mainMenuOptions = {"Admin Login", "Employee Login", "Exit"};

            int choice = getMenuChoice(mainMenuOptions);

            switch (choice) {

                case 1 -> adminLogin();

                case 2 -> employeeLogin();

                case 3 -> {
                    exitApplication();

                    break OUTER;
                }

                default -> {
                }

            }

        }

    }

    
    /**
     * Handles admin login and navigates to the admin menu on success.
     */

    private static void adminLogin() {

        System.out.print("Enter admin username: ");

        String username = scanner.nextLine().trim();

        System.out.print("Enter admin password: ");

        String password = scanner.nextLine().trim();


        if (adminService.login(username, password)) {

            adminMenu();

        } else {

            System.out.println("Invalid credentials. Try again.");

        }

    }
  
     /**
     * Handles employee login and navigates to the employee menu on success.
     */

    private static void employeeLogin() {

        System.out.print("Enter employee ID: ");

        String employeeID = scanner.nextLine().trim();


        Employee employee = adminService.findEmployee(employeeID);


        if (employee != null) {

            Store store = findStoreForEmployee(employee);


            if (store != null) {

                currentlyLoggedInEmployee = employee; // Set the currently logged-in employee

                employeeMenu(employee, store);

            } else {

                System.out.println("Store for this employee not found.");

            }

        } else {

            System.out.println("Invalid employee ID.");

        }

    }
  
    /**
     * Displays the admin menu and handles corresponding actions.
     */

    private static void adminMenu() {

        while (true) {

            System.out.println("Admin Menu:");

            String[] adminMenuOptions = {

                    "Manage Employees",

                    "Manage Stores",

                    "Manage Inventory",

                    "View Reports",

                    "Logout"

            };

            int choice = getMenuChoice(adminMenuOptions);


            switch (choice) {

                case 1 -> adminService.manageEmployees();

                case 2 -> adminService.manageStores();

                case 3 -> adminService.manageInventory();

                case 4 -> adminService.viewReports(); // Call viewReports() method

                case 5 -> {

                    return; // Logout

                }

                default -> System.out.println("Invalid choice. Please try again.");

            }

        }

    }
    
    /**
     * Displays the employee menu and handles corresponding actions.
     * 
     * @param employee Currently logged-in employee
     * @param store    Store where the employee is working
     */


    private static void employeeMenu(Employee employee, Store store) {

        while (true) {

            System.out.println("Employee Name - " + employee.getName());

            String[] employeeMenuOptions = {

                    "Take Order",

                    "View Sales Report",

                    "Logout"

            };

            int choice = getMenuChoice(employeeMenuOptions);


            switch (choice) {

                case 1 -> takeOrder(store);

                case 2 -> viewSalesReport(store);

                case 3 -> {

                    return;

                }

                default -> System.out.println("Invalid choice. Please try again.");

            }

        }

    }



    private static void takeOrder(Store store) {
    System.out.println("Enter customer name: ");
    String customerName = scanner.nextLine().trim();

    // Create a list to store the ordered items
    List<OrderItem> orderItems = new ArrayList<>();

    // Loop to allow customers to add multiple items to their order
    while (true) {
        System.out.println("Enter ice cream flavor (or 'done' to finish):");
        String iceCreamFlavor = scanner.nextLine().trim();

        if (iceCreamFlavor.equalsIgnoreCase("done")) {
            break; // Exit the loop if the customer is finished adding items
        }

        // Check if the selected flavor is in stock
        if (Validation.isItemInStock(iceCreamFlavor, store)) { 
            double price = store.getPrice(iceCreamFlavor); 

            // Apply January discount for specific flavors
            LocalDate now = LocalDate.now();
            if (now.getMonth() == java.time.Month.JANUARY && 
                    (iceCreamFlavor.equalsIgnoreCase("Kulfi") || 
                            iceCreamFlavor.equalsIgnoreCase("Rolled") || 
                            iceCreamFlavor.equalsIgnoreCase("Melty"))) {
                price *= 0.7; // Apply 30% discount
                System.out.println("January Discount: " + iceCreamFlavor + " is 30% off!");
            }

            System.out.println("Price for " + iceCreamFlavor + ": $" + price); 

            System.out.println("Enter quantity for " + iceCreamFlavor + ":");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            // Create an OrderItem object and add it to the list
            OrderItem orderItem = new OrderItem(iceCreamFlavor, price, quantity);
            orderItems.add(orderItem); 
        } else { 
            System.out.println("Sorry, we don't have " + iceCreamFlavor + " in stock."); 
        }
    }

    // Check if an employee is currently logged in
    if (currentlyLoggedInEmployee != null) { 
        String employeeID = currentlyLoggedInEmployee.getEmployeeID(); 

        // Prompt the user to select a payment method
        System.out.println("Select payment method (Cash, Credit Card, Debit Card):");
        String paymentMethod = scanner.nextLine().trim();

        // Create an Order object with the collected information
        Order order = new Order(customerName, orderItems, employeeID, paymentMethod); 
        store.addOrder(order); 

        // Display a success message
        System.out.println("Order placed successfully!");

        // Generate and display the receipt
        String receipt = order.generateReceipt();
        System.out.println(receipt);
    } else {
        System.out.println("No employee is currently logged in.");
    }
}
    
     private static Employee findEmployee(String employeeID) {
        for (Store store : adminService.getStores()) {
            for (Employee employee : store.getEmployees()) {
                if (employee.getEmployeeID().equals(employeeID)) {
                    return employee;
                }
            }
        }
        return null; 
    }

   private static void viewSalesReport(Store store) {
    System.out.println("Sales Report for " + store.getName());
    System.out.println("----------------------------------------");

    Map<String, Double> employeeSales = new HashMap<>(); 

    for (Order order : store.getOrders()) {
        String employeeID = order.getEmployeeID();
        double orderTotal = order.getTotalPrice();
        employeeSales.put(employeeID, employeeSales.getOrDefault(employeeID, 0.0) + orderTotal); 

        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Customer Name: " + order.getCustomerName());
        System.out.println("Order Date/Time: " + order.getFormattedDateTime());
        System.out.println("Payment Method: " + order.getPaymentMethod());

        System.out.println("Ordered Items:");
        for (OrderItem item : order.getOrderItems()) {
            System.out.printf(" - %s x%d - $%.2f x%d = $%.2f\n", 
                    item.getItemName(), item.getQuantity(), item.getPrice(), 
                    item.getQuantity(), item.getTotalPrice()); 
        }

        System.out.println("Total: $" + String.format("%.2f", orderTotal));
        System.out.println("Employee: " + findEmployee(employeeID).getName()); 
        System.out.println("------------------"); 
    }

    // Display Employee Sales Summary
    System.out.println("Employee Sales Summary:");
    for (Map.Entry<String, Double> entry : employeeSales.entrySet()) {
        Employee employee = findEmployee(entry.getKey()); 
        if (employee != null) {
            System.out.println(" - " + employee.getName() + ": $" + String.format("%.2f", entry.getValue()));
        }
    }

    // Display Total Sales
    System.out.println("Total Sales: $" + String.format("%.2f", employeeSales.values().stream().mapToDouble(Double::doubleValue).sum()));
    System.out.println("----------------------------------------");
}
   
    private static int getMenuChoice(String[] options) {

        while (true) {

            System.out.println("Please choose an option:");

            for (int i = 0; i < options.length; i++) {

                System.out.println((i + 1) + ". " + options[i]);

            }

            System.out.print("Your choice: ");

            try {

                int choice = scanner.nextInt();

                scanner.nextLine(); // Consume the newline character

                if (choice >= 1 && choice <= options.length) {

                    return choice;

                } else {

                    System.out.println("Invalid choice. Please try again.");

                }

            } catch (InputMismatchException e) {

                System.out.println("Please enter a valid number.");

                scanner.next(); // Clear the invalid input

            }

        }

    }


    private static void exitApplication() {

        try {

            adminService.saveStores();

        } catch (IOException e) {

            System.out.println("Error saving stores: " + e.getMessage());

        } finally {

            System.out.println("Exiting...");

            scanner.close(); // Close the scanner in the finally block

        }

    }


    private static Store findStoreForEmployee(Employee employee) {

        for (Store store : adminService.getStores()) {

            for (Employee emp : store.getEmployees()) {

                if (emp.getEmployeeID().equals(employee.getEmployeeID())) {

                    return store;

                }

            }

        }

        return null;

    }

    
} 