package domain;

public class MoneyFactory
{
    public static Money getMoney( Double denomination)
    {
        if(denomination==0.25)
        {
            Money twentyfivepaise=(new MoneyBuilder()).withQuantity(50).withDenomination(denomination).build();
            return twentyfivepaise;
        }
        else if(denomination==0.50)
        {
            Money fiftypaise=(new MoneyBuilder()).withQuantity(50).withDenomination(denomination).build();
            return fiftypaise;
        }
        else if(denomination==1.0)
        {
            Money onerupee=(new MoneyBuilder()).withQuantity(50).withDenomination(denomination).build();
            return onerupee;
        }
        else
        {
            Money fiverupee=(new MoneyBuilder()).withQuantity(50).withDenomination(denomination).build();
            return fiverupee;
        }

    }
}
