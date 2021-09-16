package domain;
import java.io.Serializable;

public class Sweets implements Serializable
{
    private Double cost;
    private int quantity;
    private String name;
    public Sweets(SweetsBuilder sweetsBuilder)
    {
        this.cost= sweetsBuilder.getCost();
        this.quantity= sweetsBuilder.getQuantity();
        this.name= sweetsBuilder.getName();

    }
    @Override
    public String toString()
    {
        return " Name= "+this.name+" Cost= "+this.cost+" Quantity= "+this.quantity+"\n";
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
