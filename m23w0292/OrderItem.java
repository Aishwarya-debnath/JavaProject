package twentyfour.fall.oop.group1.finalproject.m23w0292;


public class OrderItem {
    private String itemName; 
    private double price; 
    private int quantity; 

    public OrderItem(String itemName, double price, int quantity) { 
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity; 
    }

    @Override
    public String toString() {
        return String.format("%s x%d - $%.2f x%d = $%.2f", 
                             itemName, quantity, price, quantity, getTotalPrice()); 
    }
}