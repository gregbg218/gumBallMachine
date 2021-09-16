package domain;

public class SweetsFactory {

    public static Sweets getSweets( String name)
    {
        if(name.equals("Chocolate Frog"))
        {
            Sweets frog=(new SweetsBuilder()).withName("Chocolate Frog").withQuantity(10).withCost(10.0).build();
            return frog;
        }
        else if(name.equals("Liquorice Wand"))
        {
            Sweets wand=(new SweetsBuilder()).withName("Liquorice Wand").withQuantity(10).withCost(5.0).build();
            return wand;
        }
        else if(name.equals("Bertie Bott's Every Flavour Beans"))
        {
            Sweets bean=(new SweetsBuilder()).withName("Bertie Bott's Every Flavour Beans").withQuantity(10).withCost(8.5).build();
            return bean;
        }
        else
        {
            Sweets pastille=(new SweetsBuilder()).withName("Puking Pastille").withQuantity(10).withCost(4.25).build();
            return pastille;
        }

    }
}
