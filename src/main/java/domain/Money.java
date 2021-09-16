package domain;

import java.io.Serializable;

public class Money implements Serializable
{
    private int quantity;
    private Double denomination;

    public Money(MoneyBuilder moneyBuilder)
    {
        this.denomination= moneyBuilder.getDenomination();
        this.quantity= moneyBuilder.getQuantity();
    }

    @Override
    public String toString()
    {
        return " Denomination= "+this.denomination+" Quantity= "+this.quantity+"\n";
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getDenomination() {
        return denomination;
    }

    public void setDenomination(Double denomination) {
        this.denomination = denomination;
    }
}
