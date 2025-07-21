package twentyfour.fall.oop.group1.finalproject.m23w0292;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.format.DateTimeFormatter; 

public class Order {
    private String orderID;
    private String customerName;
    private List<OrderItem> orderItems; 
    private String employeeID; 
    private LocalDateTime orderDateTime; 
    private String paymentMethod; 
    private String receiptNumber; 

   

    public Order(String customerName, List<OrderItem> orderItems, String employeeID, String paymentMethod) {
        this.orderID = UUID.randomUUID().toString(); 
        this.customerName = customerName;
        this.orderItems = orderItems;
        this.employeeID = employeeID;
        this.orderDateTime = LocalDateTime.now();
        this.paymentMethod = paymentMethod; 
        this.receiptNumber = generateReceiptNumber(); 
    }
    // Getters
    public String getOrderID() {
        return orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    // Setters 
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFormattedDateTime() { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
        return orderDateTime.format(formatter); 
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (OrderItem item : orderItems) {
            totalPrice += item.getTotalPrice(); 
        }
        return totalPrice;
    }

    // Generate a unique receipt number 
    private String generateReceiptNumber() {
       
        return "RCPT" + String.format("%05d", (int)(Math.random() * 100000)); 
    }

    // Generate the receipt string
    public String generateReceipt() {
        String receipt = """
                ==================================
                Ice Cream Shop Receipt
                ==================================
                Order ID: %s
                Date/Time: %s
                Customer: %s
                
                Order Items:
                """;
        receipt = String.format(receipt, orderID, getFormattedDateTime(), customerName);

        for (OrderItem item : orderItems) {
            // Use the enhanced OrderItem.toString() method for better readability
            receipt += String.format(" - %s\n", item.toString()); 
        }

        receipt += String.format("\nTotal: $%.2f\n", getTotalPrice());
        receipt += String.format("Payment Method: %s\n", paymentMethod);
        receipt += "\nThank you for your order!";

        return receipt;
    }

    
}
