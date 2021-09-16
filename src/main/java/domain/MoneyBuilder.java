package domain;

public class MoneyBuilder
{
    private int quantity;
    private Double denomination;

    public MoneyBuilder withDenomination(Double denomination)
    {
        this.denomination=denomination;
        return this;
    }
    public MoneyBuilder withQuantity(int quantity)
    {
        this.quantity=quantity;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getDenomination() {
        return denomination;
    }
    public Money build() {
        return new Money(this);
    }
}
