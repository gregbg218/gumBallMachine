package domain;

public class SweetsBuilder
{
    private Double cost;
    private int quantity;
    private String name;
    //static Sweets sweets;

    public SweetsBuilder withCost(Double cost)
    {
        this.cost=cost;
        return this;
    }
    public SweetsBuilder withQuantity(int quantity)
    {
        this.quantity=quantity;
        return this;
    }
    public SweetsBuilder withName(String name)
    {
        this.name=name;
        return this;
    }

    public Double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Sweets build() {
        return new Sweets(this);
    }
}
