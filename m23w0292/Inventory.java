package twentyfour.fall.oop.group1.finalproject.m23w0292;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    // A map to store the stock of items. The key is the item name, and the value is the quantity.
    private Map<String, Integer> stock;

    // Constructor to initialize the inventory with an empty HashMap
    public Inventory() {
        stock = new HashMap<>();
    }
  
    /**
     * Adds a certain quantity of an item to the inventory.
     * If the item already exists, it increments the quantity.
     * 
     * @param itemName The name of the item to be added
     * @param quantity The quantity of the item to be added
     */
    public void addItem(String itemName, int quantity) {
        // Ensure the quantity is greater than zero
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
         // Add or update the quantity of the item in the inventory
        stock.put(itemName, stock.getOrDefault(itemName, 0) + quantity);
        System.out.println(quantity + " of " + itemName + " added to inventory.");
    }

    
   /**
     * Removes a certain quantity of an item from the inventory.
     * If the quantity to remove exceeds the available quantity, it prints a message.
     * If the item's quantity reaches zero, it removes the item from the inventory.
     * 
     * @param itemName The name of the item to be removed
     * @param quantity The quantity of the item to be removed
     */
    
    public void removeItem(String itemName, int quantity) {
        if (!stock.containsKey(itemName)) {
            System.out.println("Item not found in inventory.");
            return;
        }
        // Get the current quantity of the item in the inventory
        int currentQuantity = stock.get(itemName);
        // Ensure we do not attempt to remove more than the available quantity
        if (quantity > currentQuantity) {
            System.out.println("Cannot remove more than available. Current quantity: " + currentQuantity);
            return;
        }
        // Update the stock with the reduced quantity
        stock.put(itemName, currentQuantity - quantity);
        System.out.println(quantity + " of " + itemName + " removed from inventory.");
        
        // If the quantity of the item becomes zero, remove it from the inventory
        if (stock.get(itemName) == 0) {
            stock.remove(itemName); 
        }
    }
    
     /**
     * Displays all items in the inventory along with their quantities.
     * If the inventory is empty, it prints a message saying so.
     */

    public void displayInventory() {
        if (stock.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println("Item: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

     /**
     * Getter method to retrieve the stock map.
     * 
     * @return The map containing the stock of items in the inventory
     */
    
    public Map<String, Integer> getStock() {
        return stock;
    }
}