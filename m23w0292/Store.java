package twentyfour.fall.oop.group1.finalproject.m23w0292;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private String name;
    private Map<String, Integer> inventory; 
    private List<Employee> employees;
    private List<Order> orders;
    private IceCreamShop iceCreamShop; // Declare iceCreamShop as a class variable

    public Store(String name, IceCreamShop iceCreamShop) { 
        this.name = name;
        this.inventory = new HashMap<>();
        this.employees = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.iceCreamShop = iceCreamShop; 
    }


    public String getName() {
        return name;
    }

    public Map<String, Integer> getInventory() {
        return inventory; 
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Order> getOrders() {
        return orders; 
    }

    /**
     * Adds a specified quantity of an item to the store's inventory.
     *
     * @param itemName  The name of the item to be added
     * @param quantity The quantity of the item to be added
     */
    public void addItem(String itemName, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero for item: " + itemName);
            return;
        }
        // Add item to inventory or update existing quantity

        if (inventory.containsKey(itemName)) {
            inventory.put(itemName, inventory.get(itemName) + quantity); 
        } else {
            inventory.put(itemName, quantity); 
        }
    }

    /**
     * Removes a specified quantity of an item from the store's inventory.
     *
     * @param itemName  The name of the item to be removed
     * @param quantity The quantity of the item to be removed
     */
    public void removeItem(String itemName, int quantity) {
        if (!inventory.containsKey(itemName)) {
            System.out.println("Item not found in inventory.");
            return;
        }

        int currentQuantity = inventory.get(itemName);
        if (quantity > currentQuantity) {
            System.out.println("Cannot remove more than available. Current quantity: " + currentQuantity);
            return;
        }

        inventory.put(itemName, currentQuantity - quantity); 

        if (inventory.get(itemName) == 0) {
            inventory.remove(itemName); 
        }

        System.out.println(quantity + " of " + itemName + " removed from inventory."); 
    }
    
     /**
     * Displays the current inventory for the store.
     */

    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("Current Inventory for " + name + ":");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Item: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addOrder(Order order) {
        orders.add(order);
        // Update inventory after order is placed
        for (OrderItem item : order.getOrderItems()) {
            String itemName = item.getItemName();
            int orderQuantity = item.getQuantity();

            if (inventory.containsKey(itemName)) {
                int currentQuantity = inventory.get(itemName);
                int updatedQuantity = currentQuantity - orderQuantity;

                if (updatedQuantity < 50) {
                    System.out.println("** Low Stock Alert: ** " + itemName + " has " + updatedQuantity + " units remaining.");
                    
                }

                inventory.put(itemName, updatedQuantity);
                if (updatedQuantity <= 0) {
                    inventory.remove(itemName); 
                }
            }
        }
    }
    
    /**
     * Checks if an item exists in the store's inventory.
     *
     * @param itemName The name of the item to be checked
     * @return True if the item exists, otherwise false
     */

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName);
    } 
    
    // Price and discount methods, delegating to IceCreamShop for pricing details

    /**
     * Retrieves the price of a specific flavor.
     *
     * @param flavor The name of the ice cream flavor
     * @return The price of the flavor
     */

    public double getPrice(String flavor) {
    return iceCreamShop.getPrice(flavor); 
}
/**
     * Retrieves the discount price of a specific flavor.
     *
     * @param flavor The name of the ice cream flavor
     * @return The discounted price of the flavor
     */
    public double getDiscountPrice(String flavor) {
        return iceCreamShop.getPrice(flavor); // Delegate to IceCreamShop for price lookup
    }
}