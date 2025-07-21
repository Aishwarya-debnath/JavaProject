package twentyfour.fall.oop.group1.finalproject.m23w0292;

import java.util.HashMap;
import java.util.Map;

public class IceCreamShop {
    private Map<String, Double> flavorPrices;

    public IceCreamShop() {
        flavorPrices = new HashMap<>();
        flavorPrices.put("Gelato", 5.00);
        flavorPrices.put("Kulfi", 5.50);
        flavorPrices.put("Malai", 4.50);
        flavorPrices.put("Sherbet", 4.00);
        flavorPrices.put("Ice Popsicle", 3.50);
        flavorPrices.put("Sorbet", 4.00);
        flavorPrices.put("Rolled", 6.00);
        flavorPrices.put("Snow Cream", 4.50);
        flavorPrices.put("Yogurt", 4.00);
        flavorPrices.put("Mochi", 3.00);
        flavorPrices.put("Italian Ice", 3.50);
        flavorPrices.put("Vanilla", 5.00);
        flavorPrices.put("Melty", 5.50);
        flavorPrices.put("Fruity Bomb", 6.00);
    }

    public double getPrice(String flavor) {
        return flavorPrices.getOrDefault(flavor, 0.0); 
    }
}
