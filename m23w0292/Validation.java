package twentyfour.fall.oop.group1.finalproject.m23w0292;

public class Validation {

    public static boolean isItemInStock(String itemName, Store store) {
    return store.hasItem(itemName) && store.getInventory().get(itemName) > 0; 
}

   
    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

}