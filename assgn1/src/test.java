/**
 * Created by hp 15 on 8/19/2016.
 */

///*
//thread containing class
import java.util.*;
import java.io.*;

public class test implements Runnable
{
    public void run(){}

    static void cleanUp(stock.Queue OrderQueue)
    {
        try
        {
            PrintStream ExchangeOutput = new PrintStream(new FileOutputStream("exchange.txt", true));
            PrintStream cleanUpOutput = new PrintStream(new FileOutputStream("cleanup.txt", true));
            //ExchangeOutput.println(profit);
            stock.OrderNode node;
            while((node=OrderQueue.deque())!=null)
            {
                int time=Integer.parseInt(node.fields[0])+Integer.parseInt(node.fields[2]);
                String actionString="";
                int i;
                for(i=0;i<8;i++)
                    actionString=actionString+node.fields[i]+" ";
                cleanUpOutput.println(time+" "+actionString);
            }
        }
        catch(Exception e){}
    }
    /*
    public String actionString;
    public test(){}
    public test(String str) {actionString=str;}

    class OrderNode
    {
        public int T0;
        public String name;
        public int TExp;
        public String type;
        public int qty;
        public String stock;
        public int price;
        public boolean partial;
        public String fields[]=new String[8];
        OrderNode next;
    }

    class Queue
    {
        public Queue(OrderNode Node)
        {
            head=Node;
        }
        OrderNode head;
        public void enqueue(OrderNode node)
        {
            OrderNode temp=new OrderNode();
            if(head==null)
            {
                head=node;
                head.next=null;
                return;
            }
            temp=head;
            while(temp.next!=null)
                temp=temp.next;
            temp.next=node;
            node.next=null;
        }
    }

    public boolean isAlpha(String name)
    {
        char[] chars = name.toCharArray();
        for (char c : chars)
        {
            if(!Character.isLetter(c))
                return false;
        }
        return true;

    }

    public void run()
    {
        // ORDER THREAD
        if(Thread.currentThread().getName().equals("order"))
        {
            Scanner s=new Scanner(actionString);
            int cc=0;
            try
            {
                PrintStream out = new PrintStream(new FileOutputStream("output1.txt",true));
                while (s.hasNext())
                {
                    cc++;
                    s.next();
                }
                if(cc!=8)
                {
                    //out.println("EXCEPTION");
                    System.out.println("invalid format of order EXCEPTION");
                    return;
                }
                cc=0;
                s=new Scanner(actionString);
                int num,i;
                OrderNode node=new OrderNode();
                while(s.hasNext())
                {
                    node.fields[cc]=s.next();
                    cc++;
                }
                for(i=0;i<8;i=i+2)
                {
                    try
                    {
                        num=Integer.parseInt(node.fields[i]);
                    }
                    catch(NumberFormatException err)
                    {
                        //out.println("integer EXCEPTION coz of "+node.fields[i]);
                        System.out.println("integer EXCEPTION coz of "+node.fields[i]);
                        return;
                    }
                }
                node.fields[3]=node.fields[3].toLowerCase();
                if((!node.fields[3].equals("buy"))&&(!node.fields[3].equals("sell")))
                {
                    //out.println("EXCEPTION coz of "+node.fields[3]);
                    System.out.println("type EXCEPTION coz of "+node.fields[3]);
                    return;
                }
                if((!node.fields[7].equals("T"))&&(!node.fields[7].equals("F")))
                {
                    //out.println("EXCEPTION coz of "+node.fields[7]);
                    System.out.println("partial EXCEPTION coz of "+node.fields[7]);
                    return;
                }
                if(!isAlpha(node.fields[1]))
                {
                    //out.println("EXCEPTION coz of "+node.fields[7]);
                    System.out.println("name EXCEPTION coz of "+node.fields[1]);
                    return;
                }
                NOT WORKING - NO IDEA WHY
                node.fields[3]=node.fields[3].toLowerCase();
                if((!node.fields[3].equals("buy"))&&(!node.fields[3].equals("sell")));
                {
                    //out.println("EXCEPTION coz of "+node.fields[3]);
                    System.out.println("EXCEPTION coz of "+node.fields[3]);
                    return;
                }
            }
            catch (Exception e){}
        }

        // EXCHANGE THREAD
        if(Thread.currentThread().getName().equals("exchange"))
        {
        }

        // CLEANUP THREAD
        if(Thread.currentThread().getName().equals("cleanup"))
        {
        }
    }
    */
}
//*/
