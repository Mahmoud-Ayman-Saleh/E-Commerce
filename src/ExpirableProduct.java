import java.text.SimpleDateFormat;
import java.util.*;
public class ExpirableProduct extends Product implements IShippable
{
    private Date expirationDate;
    private double weight;

    public ExpirableProduct(String name, double price, int quantity, boolean requiresShipping, Date expirationDate, double weight)
    {
        super(name, price, quantity, requiresShipping);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }
    @Override
    public boolean isExpired()
    {
        return new Date().after(expirationDate);
    }

    @Override
    public String toFileString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("EXPIRABLE|%s|%.2f|%d|%b|%s|%.2f", name, price, quantity, requiresShipping, sdf.format(expirationDate), weight);
    }

    @Override
    public double getWeight()
    {
        return weight;
    }
    
}
