import java.text.SimpleDateFormat;
import java.util.*;
public class ECommerceSystem
{
    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        products = DatabaseManager.loadProducts();
        customers = DatabaseManager.loadCustomers();
        
        // Add some default customers if none exist
        if (customers.isEmpty())
        {
            customers.add(new Customer("Mohamed Said", 1000.0));
            customers.add(new Customer("Jane Smith", 500.0));
            DatabaseManager.saveCustomers(customers);
        }
        
        System.out.println("Welcome to Dynamic E-Commerce System!");
        
        while (true)
        {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Admin Panel");
            System.out.println("2. Customer Panel");
            System.out.println("3. Exit");
            System.out.print("Choose your role: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice)
            {
                case 1:
                    adminPanel();
                    break;
                case 2:
                    customerPanel();
                    break;
                case 3:
                    System.out.println("Thank you for using our system!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void adminPanel()
    {
        while (true)
        {
            System.out.println("\n=== ADMIN PANEL ===");
            System.out.println("1. Add New Product");
            System.out.println("2. Edit Existing Product");
            System.out.println("3. View All Products");
            System.out.println("4. Remove Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice)
            {
                case 1:
                    addProduct();
                    break;
                case 2:
                    editProduct();
                    break;
                case 3:
                    viewAllProducts();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addProduct()
    {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        
        // Check if product already exists
        for (Product product : products)
        {
            if (product.getName().equalsIgnoreCase(name))
            {
                System.out.println("Product already exists! Use Edit option instead.");
                return;
            }
        }
        
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        
        System.out.print("Does it require shipping? (true/false): ");
        boolean requiresShipping = scanner.nextBoolean();
        
        double weight = 0;
        if (requiresShipping)
        {
            System.out.print("Enter weight in grams: ");
            weight = scanner.nextDouble();
        }
        
        System.out.print("Does it expire? (true/false): ");
        boolean expires = scanner.nextBoolean();
        scanner.nextLine();
        
        Product newProduct;
        if (expires)
        {
            System.out.print("Enter expiry date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date expiryDate = sdf.parse(dateStr);
                newProduct = new ExpirableProduct(name, price, quantity, requiresShipping, expiryDate, weight);
            }
            catch (Exception e)
            {
                System.out.println("Invalid date format. Product not added.");
                return;
            }
        }
        else
        {
            newProduct = new NonExpirableProduct(name, price, quantity, requiresShipping, weight);
        }
        
        products.add(newProduct);
        DatabaseManager.saveProducts(products);
        System.out.println("Product added successfully!");
    }
    
    private static void editProduct()
    {
        if (products.isEmpty())
        {
            System.out.println("No products available to edit.");
            return;
        }
        
        System.out.println("Available products:");
        for (int i = 0; i < products.size(); i++)
        {
            Product p = products.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " - $" + p.getPrice() + " (Qty: " + p.getQuantity() + ")");
        }
        
        System.out.print("Select product to edit (number): ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (index < 0 || index >= products.size())
        {
            System.out.println("Invalid selection.");
            return;
        }
        
        Product product = products.get(index);
        
        System.out.println("Editing: " + product.getName());
        System.out.println("1. Change name");
        System.out.println("2. Change price");
        System.out.println("3. Change quantity");
        System.out.println("4. Change shipping requirement");
        if (product instanceof ExpirableProduct)
        {
            System.out.println("5. Change expiry date");
        }
        if (product.isRequiresShipping())
        {
            System.out.println("6. Change weight");
        }
        
        System.out.print("What would you like to change? ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice)
        {
            case 1:
                System.out.print("Enter new name: ");
                product.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new price: ");
                product.setPrice(scanner.nextDouble());
                break;
            case 3:
                System.out.print("Enter new quantity: ");
                product.setQuantity(scanner.nextInt());
                break;
            case 4:
                System.out.print("Requires shipping? (true/false): ");
                boolean shipping = scanner.nextBoolean();
                product.setRequiresShipping(shipping);
                if (shipping && product instanceof IShippable)
                {
                    System.out.print("Enter weight in grams: ");
                    double weight = scanner.nextDouble();
                    if (product instanceof ExpirableProduct)
                    {
                        ((ExpirableProduct) product).setWeight(weight);
                    }
                    else if (product instanceof NonExpirableProduct)
                    {
                        ((NonExpirableProduct) product).setWeight(weight);
                    }
                }
                break;
            case 5:
                if (product instanceof ExpirableProduct)
                {
                    System.out.print("Enter new expiry date (yyyy-MM-dd): ");
                    String dateStr = scanner.nextLine();
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date expiryDate = sdf.parse(dateStr);
                        ((ExpirableProduct) product).setExpirationDate(expiryDate);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Invalid date format.");
                        return;
                    }
                }
                break;
            case 6:
                if (product.isRequiresShipping())
                {
                    System.out.print("Enter new weight in grams: ");
                    double weight = scanner.nextDouble();
                    if (product instanceof ExpirableProduct)
                    {
                        ((ExpirableProduct) product).setWeight(weight);
                    }
                    else if (product instanceof NonExpirableProduct)
                    {
                        ((NonExpirableProduct) product).setWeight(weight);
                    }
                }
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        DatabaseManager.saveProducts(products);
        System.out.println("Product updated successfully!");
    }
    
    private static void viewAllProducts()
    {
        if (products.isEmpty())
        {
            System.out.println("No products available.");
            return;
        }
        
        System.out.println("\n=== ALL PRODUCTS ===");
        for (Product product : products)
        {
            System.out.println("Name: " + product.getName());
            System.out.println("Price: $" + product.getPrice());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Requires Shipping: " + product.isRequiresShipping());
            if (product instanceof ExpirableProduct)
            {
                ExpirableProduct ep = (ExpirableProduct) product;
                System.out.println("Expiry Date: " + ep.getExpirationDate());
                System.out.println("Expired: " + ep.isExpired());
                if (product.isRequiresShipping())
                {
                    System.out.println("Weight: " + ep.getWeight() + "g");
                }
            }
            else
            {
                System.out.println("Non-expirable product");
                if (product.isRequiresShipping())
                {
                    System.out.println("Weight: " + ((NonExpirableProduct) product).getWeight() + "g");
                }
            }
            System.out.println("---");
        }
    }
    
    private static void removeProduct()
    {
        if (products.isEmpty())
        {
            System.out.println("No products available to remove.");
            return;
        }
        
        System.out.println("Available products:");
        for (int i = 0; i < products.size(); i++)
        {
            Product p = products.get(i);
            System.out.println((i + 1) + ". " + p.getName());
        }
        
        System.out.print("Select product to remove (number): ");
        int index = scanner.nextInt() - 1;
        
        if (index < 0 || index >= products.size())
        {
            System.out.println("Invalid selection.");
            return;
        }
        
        Product removed = products.remove(index);
        DatabaseManager.saveProducts(products);
        System.out.println("Product '" + removed.getName() + "' removed successfully!");
    }
    
    private static void customerPanel()
    {
        
        if (customers.isEmpty())
        {
            System.out.println("No customers available.");
            return;
        }
        
        System.out.println("Available customers:");
        for (int i = 0; i < customers.size(); i++)
        {
            Customer c = customers.get(i);
            System.out.println((i + 1) + ". " + c.getName() + " (Balance: $" + c.getBalance() + ")");
        }
        
        System.out.print("Select customer (number): ");
        int customerIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (customerIndex < 0 || customerIndex >= customers.size())
        {
            System.out.println("Invalid selection.");
            return;
        }
        
        Customer customer = customers.get(customerIndex);
        Cart cart = new Cart();
        
        while (true)
        {
            System.out.println("\n=== CUSTOMER PANEL - " + customer.getName() + " ===");
            System.out.println("Balance: $" + customer.getBalance());
            System.out.println("1. View Available Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice)
            {
                case 1:
                    viewAvailableProducts();
                    break;
                case 2:
                    addToCart(cart);
                    break;
                case 3:
                    viewCart(cart);
                    break;
                case 4:
                    checkout(customer, cart);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void viewAvailableProducts()
    {
        if (products.isEmpty())
        {
            System.out.println("No products available.");
            return;
        }
        
        System.out.println("\n=== AVAILABLE PRODUCTS ===");
        for (int i = 0; i < products.size(); i++)
        {
            Product product = products.get(i);
            if (product.getQuantity() > 0 && !product.isExpired())
            {
                System.out.println((i + 1) + ". " + product.getName() + " - $" + product.getPrice() + " (Available: " + product.getQuantity() + ")");
            }
        }
    }
    
    private static void addToCart(Cart cart)
    {
        if (products.isEmpty())
        {
            System.out.println("No products available.");
            return;
        }
        
        viewAvailableProducts();
        
        System.out.print("Select product to add (number): ");
        int productIndex = scanner.nextInt() - 1;
        
        if (productIndex < 0 || productIndex >= products.size())
        {
            System.out.println("Invalid selection.");
            return;
        }
        
        Product product = products.get(productIndex);
        
        if (product.getQuantity() == 0)
        {
            System.out.println("Product is out of stock.");
            return;
        }
        
        if (product.isExpired())
        {
            System.out.println("Product is expired.");
            return;
        }
        
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        
        cart.add(product, quantity);
    }
    
    private static void viewCart(Cart cart)
    {
        if (cart.isEmpty())
        {
            System.out.println("Cart is empty.");
            return;
        }
        
        System.out.println("\n=== CART CONTENTS ===");
        for (CartItem item : cart.getItems())
        {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " - $" + item.getTotalPrice());
        }
        System.out.println("Subtotal: $" + cart.getSubtotal());
    }
    
    private static void checkout(Customer customer, Cart cart)
    {
        if (cart.isEmpty())
        {
            System.out.println("Error: Cart is empty");
            return;
        }
        
        // Check stock and expiry
        for (CartItem item : cart.getItems())
        {
            Product product = item.getProduct();
            if (product.getQuantity() < item.getQuantity())
            {
                System.out.println("Error: " + product.getName() + " is out of stock");
                return;
            }
            if (product.isExpired())
            {
                System.out.println("Error: " + product.getName() + " is expired");
                return;
            }
        }
        
        // Calculate totals
        double subtotal = cart.getSubtotal();
        List<IShippable> IshippableItems = new ArrayList<>();
        
        for (CartItem item : cart.getItems())
        {
            if (item.getProduct().isRequiresShipping())
            {
                for (int i = 0; i < item.getQuantity(); i++)
                {
                    IshippableItems.add((IShippable) item.getProduct());
                }
            }
        }
        
        double shippingFee = ShippingService.calculateShippingCost(IshippableItems);
        double totalAmount = subtotal + shippingFee;
        
        if (customer.getBalance() < totalAmount)
        {
            System.out.println("Error: Insufficient balance. Required: $" + totalAmount + ", Available: $" + customer.getBalance());
            return;
        }
        
        // Process payment
        customer.deductBalance(totalAmount);
        
        // Update product quantities
        for (CartItem item : cart.getItems())
        {
            item.getProduct().decreaseQuantity(item.getQuantity());
        }
        
        // Save updated data
        DatabaseManager.saveProducts(products);
        DatabaseManager.saveCustomers(customers);
        
        // Print receipt
        if (!IshippableItems.isEmpty())
        {
            ShippingService.ship(IshippableItems);
        }
        
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems())
        {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + " " + item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + subtotal);
        System.out.println("Shipping " + shippingFee);
        System.out.println("Amount " + totalAmount);
        System.out.println("Customer balance after payment: $" + customer.getBalance());
        System.out.println("END.");
        
        cart.clear();
        System.out.println("Checkout completed successfully!");
    }
}