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
            scanner.nextLine(); // consume newline
            
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
            scanner.nextLine(); // consume newline
            
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
        scanner.nextLine(); // consume newline
        
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
        scanner.nextLine(); // consume newline
        
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
    

}