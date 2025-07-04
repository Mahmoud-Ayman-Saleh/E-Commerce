import java.util.List;

public class ShippingService
{
    private static final double SHIPPING_RATE = 10.0;
    public static void ship(List<IShippable> items)
    {
        if (items.isEmpty()||items == null) return;

        System.out.println("** Shipment notice **");
        double totalWeight = 0.0;
        for (IShippable item : items)
        {
            System.out.println(item.getName() + " " + item.getWeight() + "g");
            totalWeight += item.getWeight();
        }
        System.out.println("Total package weight " + (totalWeight / 1000) + "kg");
    }

    public static double calculateShippingCost(List<IShippable> items)
    {
        double totalWeight = 0.0;
        for (IShippable item : items)
        {
            totalWeight += item.getWeight();
        }
        // convert grams to kilograms first then multiply by the shipping rate
        return (totalWeight/1000) * SHIPPING_RATE;
    }
}
