abstract class Product
{
    protected String name;
    protected double price;
    protected int quantity;
    protected boolean requiresShipping;

    public Product(String name, double price, int quantity, boolean requiresShipping)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.requiresShipping = requiresShipping;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    public boolean isRequiresShipping()
    {
        return requiresShipping;
    }
    public void setRequiresShipping(boolean requiresShipping)
    {
        this.requiresShipping = requiresShipping;
    }

    public abstract boolean isExpired();
    public abstract String toFileString();

    public void decreaseQuantity(int amount)
    {
        if (amount <= quantity)
        {
            quantity -= amount;
        }
        else
        {
            throw new IllegalArgumentException("Insufficient quantity available.");
        }
    }

}
