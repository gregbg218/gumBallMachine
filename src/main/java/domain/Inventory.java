package domain;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

//import bootstrap.Driver;


public class Inventory implements Serializable
{
    private Double amount;
    private int noteCount;
    private int quantity;
    private String name;

    private HashMap<String,Sweets> sweetStock;
    private LinkedHashMap<String,Integer> input;
    private LinkedHashMap<Double,Money> coinStock;

    public Inventory()
    {
        initialStocking();
    }
    public Inventory(  String name, int quantity,LinkedHashMap<String,Integer> input)
    {
        this.name=name;
        this.quantity=quantity;
        this.input=input;
        initialStocking();
    }

    public void initialStocking()
    {
        sweetStock=new HashMap<String,Sweets>();
        coinStock=new LinkedHashMap<Double,Money>();

        Sweets frog=SweetsFactory.getSweets("Chocolate Frog");
        Sweets wand=SweetsFactory.getSweets("Liquorice Wand");
        Sweets bean=SweetsFactory.getSweets("Bertie Bott's Every Flavour Beans");
        Sweets pastille=SweetsFactory.getSweets("Puking Pastille");

        sweetStock.put("Chocolate Frog",frog);
        sweetStock.put("Liquorice Wand",wand);
        sweetStock.put("Bertie Bott's Every Flavour Beans",bean);
        sweetStock.put("Puking Pastille",pastille);

        Money twentyfivepaise=MoneyFactory.getMoney(0.25);
        Money fiftypaise=MoneyFactory.getMoney(0.50);
        Money onerupee=MoneyFactory.getMoney(1.0);
        Money fiverupee=MoneyFactory.getMoney(5.0);

        coinStock.put(5.0,fiverupee);
        coinStock.put(1.0,onerupee);
        coinStock.put(0.50,fiftypaise);
        coinStock.put(0.25,twentyfivepaise);
    }

    public static String configureLogging(String logFile, String logLevel) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        String logFilename = "";
        switch (logLevel) {
            case "DEBUG": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "WARN": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.WARN_INT));
            }
            case "ERROR": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.ERROR_INT));
            }
            default: {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }

        System.out.println("Log files written out at " + logFile);
        dailyRollingFileAppender.setFile(logFile);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }

    public void transact()
    {
        amount=5*input.get("Rs 5 Coins")+1*input.get("Rs 1 Coins")+0.5*input.get("50 Paise Coins")+0.25*input.get("25 Paise Coins")+5*input.get("Rs 5 Notes");

        if(checkSweetStock(name))
        {
            System.out.println("Required quantity of "+name+" is out of stock");
//            Driver.logger.info("");
            cancelAndRefund();
        }
        else
        {
            Double change=amount-((sweetStock.get(name)).getCost())*quantity;
            if(change==0.0)
            {
                System.out.println("Transaction successful here is your "+quantity+" "+name);
                changeSweetStock();
                changeMoneyStock();
            }
            else if(change>0.0)
            {
                if(checkChangeStock(change))
                {
                    System.out.println("Transaction successful here is your "+quantity+" "+name);
                    System.out.println("Here is your change");
                    changeMoneyStock();
                    change=calculateChange(change,5.0);
                    change=calculateChange(change,1.0);
                    change=calculateChange(change,0.5);
                    change=calculateChange(change,0.25);
                    changeSweetStock();
                }
                else
                {
                    System.out.println("Sorry we are out of change");
                    cancelAndRefund();
                }
            }
            else
            {
                System.out.println("Insufficient amount paid");
                cancelAndRefund();
            }
        }
    }

    public Boolean checkSweetStock(String name)
    {
        int stock=sweetStock.get(name).getQuantity();
        if(stock<quantity)
        {
            return true;
        }
        return false;
    }

    public void cancelAndRefund()
    {
        System.out.println("Transaction cancelled");
        System.out.println("Here is your refund");
        for(String denomination: input.keySet())
        {
            System.out.println(input.get(denomination)+" "+denomination);
        }
    }

    public void changeSweetStock()
    {
        int newQuantity=sweetStock.get(name).getQuantity()-quantity;
        sweetStock.get(name).setQuantity(newQuantity);
    }

    public Boolean checkChangeStock(Double change)
    {
        Double sum=0.25*(coinStock.get(0.25).getQuantity())+0.50*(coinStock.get(0.50).getQuantity())+1.0*(coinStock.get(1.0).getQuantity())+5.0*(coinStock.get(5.0).getQuantity());
        //System.out.println("We have coins sum "+sum);
        if(change>sum)
            return false;
        if(change%0.5==0.0 ||change%0.25==0.0 ||change%5.0==0.0 ||change%1.0==0.0 )
            return true;
        return false;
    }

    public void changeMoneyStock()
    {
        ArrayList<String> denominations=new ArrayList<String>();
        int i=0;
        for(String denomination: input.keySet())
        {
            denominations.add(denomination);

        }

        for(Double denomination: coinStock.keySet())
        {
            int count=coinStock.get(denomination).getQuantity()+input.get((denominations.get(i)));
            coinStock.get(denomination).setQuantity(count);
            i++;
        }
        noteCount=noteCount+input.get((denominations.get(i)));
    }

    public  Double calculateChange(Double change,Double denomination)
    {
        int i = (int) (change / denomination);
        int count = coinStock.get(denomination).getQuantity();
        if (i <= count) {
            // System.out.println("entering if 5 " + change + " " + i);
            change = change - denomination * i;
            coinStock.get(denomination).setQuantity(count - i);
            System.out.println(i+" coins of Rs "+denomination );
        } else {
            //System.out.println("entering else 5 " + change);
            change = change - denomination * count;
            coinStock.get(denomination).setQuantity(0);
            System.out.println(0.0+" coins of Rs "+denomination );
        }
        return change;
    }

    public void refill()
    {
        for(String sweet:sweetStock.keySet())
        {
            sweetStock.get(sweet).setQuantity(10);
        }
        for(Double coin:coinStock.keySet())
        {
            coinStock.get(coin).setQuantity(50);
        }
        System.out.println("Gumball machines has been restocked");
    }

    @Override
    public String toString()
    {
        return sweetStock+" \n"+coinStock+" \n{5 Rs Note:"+noteCount+" }";
    }
    public HashMap<String, Sweets> getSweetStock() {
        return sweetStock;
    }
    public void setSweetStock(HashMap<String, Sweets> sweetStock) {
        this.sweetStock = sweetStock;
    }
    public LinkedHashMap<Double, Money> getCoinStock() {
        return coinStock;
    }
    public void setCoinStock(LinkedHashMap<Double, Money> coinStock) {
        this.coinStock = coinStock;
    }
    public int getNoteCount() {
        return noteCount;
    }
    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public LinkedHashMap<String, Integer> getInput() {
        return input;
    }
    public void setInput(LinkedHashMap<String, Integer> input) {
        this.input = input;
    }
}

