/**
 * Created by hp 15 on 8/19/2016.
 */
//Perform I/O operation
//can add extra methods

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;
import java.io.*;

public class stock
{
    Queue OrderQueue;
    Queue Buy;
    Queue Sell;
    int profit=0;
    static long startingTime=System.currentTimeMillis();

    stock()
    {
        OrderQueue=new Queue();
        Buy=new Queue();
        Sell=new Queue();
    }

    static long timeFromStart() {return (System.currentTimeMillis()-startingTime)/1000;}

    class OrderNode
    {
        String fields[]=new String[8];
        OrderNode next;
        OrderNode(){next=null;}
    }

    class Queue
    {
        OrderNode head;
        Queue(){head=null;}

        void enqueue(OrderNode node)
        {
            if(head==null)
            {
                head=node;
                return;
            }
            OrderNode temp=head;
            while(temp.next!=null)
                temp=temp.next;
            temp.next=node;
        }

        public OrderNode deque()
        {
            OrderNode temp=head;
            if(head!=null)
                head=head.next;
            return temp;
        }

        public int size()
        {
            if(head==null)
                return 0;
            OrderNode temp=head;
            int len=1;
            while(temp.next!=null)
            {
                len++;
                temp=temp.next;
            }
            return len;
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

    void performAction(String actionString)
    {
        Scanner s=new Scanner(actionString);
        int cc=0;
        try
        {
            PrintStream OrderOutput = new PrintStream(new FileOutputStream("order.out", true));
            while (s.hasNext())
            {
                cc++;
                s.next();
            }
            if (cc != 8)
            {
                OrderOutput.println("EXCEPTION " + actionString);
                //System.out.println("incorrect number of fields EXCEPTION");
                return;
            }
            cc = 0;
            s = new Scanner(actionString);
            int i,num;
            OrderNode node = new OrderNode();
            while (s.hasNext())
            {
                node.fields[cc] = s.next();
                cc++;
            }
            for (i = 0; i < 8; i = i + 2)
            {
                try
                {
                    num=Integer.parseInt(node.fields[i]);
                }
                catch (NumberFormatException err)
                {
                    OrderOutput.println("EXCEPTION " + actionString);
                    //System.out.println("integer EXCEPTION coz of " + node.fields[i]);
                    return;
                }
            }
            if(Integer.parseInt(node.fields[4])<0)
            {
                OrderOutput.println("EXCEPTION " + actionString);
                //System.out.println("negative qty EXCEPTION coz of " + node.fields[4]);
                return;
            }
            if ((!node.fields[7].equals("T")) && (!node.fields[7].equals("F")))
            {
                OrderOutput.println("EXCEPTION " + actionString);
                //System.out.println("partial EXCEPTION coz of " + node.fields[7]);
                return;
            }
            if (!isAlpha(node.fields[1]))
            {
                OrderOutput.println("EXCEPTION " + actionString);
                //System.out.println("name EXCEPTION coz of " + node.fields[1]);
                return;
            }
            node.fields[3] = node.fields[3].toLowerCase();
            if ((!node.fields[3].equals("buy")) && (!node.fields[3].equals("sell")))
            {
                OrderOutput.println("EXCEPTION " + actionString);
                //System.out.println("type EXCEPTION coz of " + node.fields[3]);
                return;
            }
            //OrderQueue.enqueue(node);
            int T0 = Integer.parseInt(node.fields[0]);
            while (timeFromStart() < T0) {}
            OrderOutput.println(timeFromStart() + " " + actionString);
            profit = profit + Exchange.OrderMatching(Buy, Sell, node);
            //System.out.println(node.fields[1] + " " + profit + ", size of buy " + Buy.size() + ", size of sell " + Sell.size());
        }
        catch (Exception e){}
    }
///*
    void cleanUp()
    {
        //test.cleanUp(OrderQueue);
        ///*
        try
        {
            PrintStream ExchangeOutput = new PrintStream(new FileOutputStream("exchange.out", true));
            PrintStream cleanUpOutput = new PrintStream(new FileOutputStream("cleanup.out", true));
            ExchangeOutput.println(profit);
            OrderNode node;
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
        //*/
    }
//*/
}
