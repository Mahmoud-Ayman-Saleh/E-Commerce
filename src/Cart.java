import java.util.ArrayList;
import java.util.List;

public class Cart
{
    private List<CartItem> items;
    public Cart()
    {
        this.items = new ArrayList<>();
    }
    public void add(Product product, int quantity)
    {
        if (quantity > product.getQuantity())
        {
            System.out.println("Error: Cannot add " + quantity + " items. Only " + product.getQuantity() + " available.");
            return;
        }

        for (CartItem item : items)
        {
            if (item.getProduct().getName().equals(product.getName()))
            {
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > product.getQuantity())
                {
                    System.out.println("Error: Cannot add " + quantity + " more items. Only " + (product.getQuantity() - item.getQuantity()) + " more available.");
                    return;
                }
                item.setQuantity(newQuantity);
                System.out.println("Added " + quantity + " " + product.getName() + " to cart.");
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        System.out.println("Added " + quantity + " " + product.getName() + " to cart.");
    }

        public List<CartItem> getItems() 
        {
            return items;
        }

        public boolean isEmpty() 
        {
            return items.isEmpty();
        }

        public void clear() 
        {
            items.clear();
        }

        public double getSubtotal()
        {
            double subtotal = 0;
            for (CartItem item : items)
            {
                subtotal += item.getTotalPrice();
            }
            return subtotal;
        }
}
