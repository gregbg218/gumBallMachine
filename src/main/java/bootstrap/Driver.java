package bootstrap;
import domain.Inventory;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Scanner;


public class Driver{
     static Logger logger= LoggerFactory.getLogger(Driver.class);

    public static void main(String args[])
    {
        configureLogging("gumBall.log","INFO");
        logger.info("Starting GumBall Machine");

        int choice;
        String serializedFileName="gumbal.dat";
        LinkedHashMap<String,Integer> input=new LinkedHashMap<String,Integer>();
        Scanner sc= new Scanner(System.in);
        Inventory gumballMachine=new Inventory();

        try
        {
            gumballMachine=deSerialize(serializedFileName);
        }
        catch (Exception e)
        {
            System.out.println("There was an error "+e);
            logger.error("There was an error ",e.toString());
        }


        do{
            System.out.println("\n#####GUMBALL MACHINE#####\n");
            System.out.println("1)Deposit Money");
            System.out.println("2)Check inventory");
            System.out.println("3)Restock machine");
            System.out.println("4)Exit");
            choice= sc.nextInt();


            switch(choice){
                case 1:
                    System.out.println("Choose a sweet:");
                    System.out.println("1)Chocolate Frog Cost: Rs 10");
                    System.out.println("2)Liquorice Wand Cost: Rs 5");
                    System.out.println("3)Bertie Bott's Every Flavour Beans Cost: Rs 8.5");
                    System.out.println("4)Puking Pastille Cost: Rs 4.25");

                    int itemNumber= sc.nextInt();
                    String name=getSweetType(itemNumber);

                    System.out.println("Enter quantity");
                    int quantity= sc.nextInt();

                    System.out.println("Enter number of Rs 5 coins ");
                    input.put("Rs 5 Coins",(sc.nextInt()));
                    System.out.println("Enter number of Rs 1 coins ");
                    input.put("Rs 1 Coins",(sc.nextInt()));
                    System.out.println("Enter number of 50 Paise coins ");
                    input.put("50 Paise Coins",(sc.nextInt()));
                    System.out.println("Enter number of 25 Paise coins ");
                    input.put("25 Paise Coins",(sc.nextInt()));
                    System.out.println("Enter number of Rs 5 notes ");
                    input.put("Rs 5 Notes",(sc.nextInt()));

                    if(gumballMachine==null)
                    {
                        gumballMachine=new Inventory(name,quantity,input);
                        //System.out.println("null so using parameter const");
                    }

                    else
                    {
                        gumballMachine.setName(name);
                        gumballMachine.setQuantity(quantity);
                        gumballMachine.setInput(input);
                        //System.out.println("setting old ");
                    }
                    gumballMachine.transact();

                    break;

                    case 2:
                        System.out.println(gumballMachine);
                        break;
                    case 3:
                        //fileContent=fileContent+transaction.getFileContent();
                        //backup=transaction;
                        gumballMachine.refill();
                        //fileContent=fileContent+"\nMachine has been restocked";
                        break;
                    case 4:
                        System.out.println("Shutting down machine");
                        try
                        {
                            serialize(gumballMachine,serializedFileName);
                        }
                        catch(IOException io)
                        {
                            System.out.println(("There was an error "+io));
                            logger.error("There was an error ",io.toString());
                        }
                        break;

//                default:
//                    System.out.println("Invalid option choose again");
            }

        }while(choice!=4);
//        fileContent=" \nStarting Gumball Machine"+fileContent+transaction.getFileContent()+"\n Machine has been shutdown";
//        FileOutputStream outputStream = new FileOutputStream("log.txt", true);
//        byte[] strToBytes = fileContent.getBytes();
//        outputStream.write(strToBytes);
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

    public static Inventory deSerialize(String serializedFileName) throws IOException,FileNotFoundException,ClassNotFoundException
    {

        try
        {
            ObjectInputStream in= new ObjectInputStream(new FileInputStream(serializedFileName));
            Inventory gumballMachine=(Inventory)in.readObject();
            //System.out.println("Inside try of serial");
            return gumballMachine;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println(("There was an error "+c));
            logger.error("There was an error ",c.toString());
            //System.out.println("Inside catch of serial");
            return new Inventory() ;
        }
        catch (FileNotFoundException f)
        {
            System.out.println(("There was an error "+f));
            logger.error("There was an error ",f.toString());
            //System.out.println("Inside catch of serial");
            return new Inventory() ;
        }
        catch (IOException io)
        {
            System.out.println(("There was an error "+io));
            logger.error("There was an error ",io.toString());
            //System.out.println("Inside catch of serial");
            return new Inventory() ;
        }

    }

    public static void serialize(Inventory gumballMachine,String serializedFileName) throws IOException
    {
        ObjectOutputStream objout=new ObjectOutputStream(new FileOutputStream(new File(serializedFileName)));
        objout.writeObject(gumballMachine);
        objout.close();
    }



    public static String getSweetType(int itemNumber )
    {
      String type;
      if(itemNumber==1)
      {
          type="Chocolate Frog";
      }
      else if (itemNumber==2)
      {
          type="Liquorice Wand";
      }
      else if (itemNumber==3)
      {
          type="Bertie Bott's Every Flavour Beans";
      }
      else
      {
          type="Puking Pastille";
      }
      return type;
    }
}
