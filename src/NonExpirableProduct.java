public class NonExpirableProduct extends Product implements IShippable
{
    private double weight;
    public NonExpirableProduct(String name, double price, int quantity, boolean requiresShipping, double weight)
    {
        super(name, price, quantity, requiresShipping);
        this.weight = weight;
    }

    @Override
    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    @Override
    public boolean isExpired()
    {
        return false;
    }

    @Override
    public String toFileString() {
        return String.format("NON_EXPIRABLE|%s|%.2f|%d|%b|%.2f", name, price, quantity, requiresShipping, weight);
    }

}
