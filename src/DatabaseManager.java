
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
class DatabaseManager
{
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String CUSTOMERS_FILE = "customers.txt";
    
    public static void saveProducts(List<Product> products)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUCTS_FILE)))
        {
            for (Product product : products)
            {
                writer.println(product.toFileString());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }
    
    public static List<Product> loadProducts()
    {
        List<Product> products = new ArrayList<>();
        File file = new File(PRODUCTS_FILE);
        
        if (!file.exists())
        {
            return products;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                if (parts.length >= 5)
                {
                    String type = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    boolean requiresShipping = Boolean.parseBoolean(parts[4]);
                    
                    if (type.equals("EXPIRABLE") && parts.length >= 7)
                    {
                        Date expiryDate = sdf.parse(parts[5]);
                        double weight = Double.parseDouble(parts[6]);
                        products.add(new ExpirableProduct(name, price, quantity, requiresShipping, expiryDate, weight));
                    }
                    else if (type.equals("NON_EXPIRABLE") && parts.length >= 6)
                    {
                        double weight = Double.parseDouble(parts[5]);
                        products.add(new NonExpirableProduct(name, price, quantity, requiresShipping, weight));
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error loading products: " + e.getMessage());
        }
        
        return products;
    }
    
    public static void saveCustomers(List<Customer> customers)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE)))
        {
            for (Customer customer : customers)
            {
                writer.println(customer.getName() + "|" + customer.getBalance());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }
    
    public static List<Customer> loadCustomers()
    {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        
        if (!file.exists())
        {
            return customers;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                if (parts.length >= 2)
                {
                    String name = parts[0];
                    double balance = Double.parseDouble(parts[1]);
                    customers.add(new Customer(name, balance));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error loading customers: " + e.getMessage());
        }
        
        return customers;
    }
}