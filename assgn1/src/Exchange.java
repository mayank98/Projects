/**
 * Created by hp 15 on 8/19/2016.
 */
//match orders

import java.io.*;
import java.util.*;

public class Exchange
{
    static int OrderMatching(stock.Queue buy,stock.Queue sell,stock.OrderNode orderNode)
    {
        int i;
        String type = orderNode.fields[3];
        String stockName = orderNode.fields[5];
        boolean partial1 = orderNode.fields[7].equals("T");
        boolean partial2;
        int qty1 = Integer.parseInt(orderNode.fields[4]);
        int qty2;
        int buyingPrice, sellingPrice;
        int spread;
        int position=0;
        int profit = 0;
        String actionString="";
        for(i=0;i<8;i++)
            actionString=actionString+orderNode.fields[i]+" ";
        stock.OrderNode temp;
        HashMap <Integer, Integer> record = new HashMap<>();
        try
        {
            PrintStream ExchangeOutput = new PrintStream(new FileOutputStream("exchange.out", true));
            if (type.equals("buy"))
            {
                buyingPrice = Integer.parseInt(orderNode.fields[6]);
                temp = sell.head;
                for (i = 0; i < sell.size(); i++)
                {
                    int t=Integer.parseInt(temp.fields[0]);
                    int tExp=Integer.parseInt(temp.fields[2]);
                    if(stock.timeFromStart()>t+tExp)
                    {
                        temp=temp.next;
                        continue;
                    }
                    sellingPrice = Integer.parseInt(temp.fields[6]);
                    spread = buyingPrice - sellingPrice;
                    if ((temp.fields[5].equals(stockName)) && (spread >= 0))
                    {
                        partial2 = temp.fields[7].equals("T");
                        qty2 = Integer.parseInt(temp.fields[4]);
                        if ((!partial1) && (!partial2))
                        {
                            if (qty1 == qty2)
                                record.put(i, spread * qty2);
                        }
                        if ((!partial1) && (partial2))
                        {
                            if (qty1 <= qty2)
                                record.put(i, spread * qty1);
                        }
                        if ((partial1) && (!partial2))
                        {
                            if (qty1 >= qty2)
                                record.put(i, spread * qty2);
                        }
                        if ((partial1) && (partial2))
                            record.put(i, spread * Math.min(qty1, qty2));
                    }
                    temp = temp.next;
                }
                Iterator it = record.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry pair = (Map.Entry) it.next();
                    profit = Math.max(profit, (int) pair.getValue());
                }
                it = record.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (profit == (int) pair.getValue())
                    {
                        position = (int) pair.getKey();
                        break;
                    }
                }
                temp = sell.head;
                for(i=1;i<position;i++)
                    temp=temp.next;
                if(temp!=null)
                {
                    if(record.isEmpty())
                    {
                        buy.enqueue(orderNode);
                        ExchangeOutput.println("P "+stock.timeFromStart()+" 0 "+actionString);
                        //System.out.println("added to buy list");
                    }
                    else
                    {
                        qty2 = Integer.parseInt(temp.fields[4]);
                        String qty=Integer.toString(Math.min(qty1, qty2));
                        if (Math.min(qty1, qty2) == qty1)
                        {
                            orderNode.fields[4] = "0";
                            temp.fields[4] = Integer.toString(qty2 - qty1);
                        }
                        else
                        {
                            orderNode.fields[4] = Integer.toString(qty1 - qty2);;
                            temp.fields[4] = "0";
                            buy.enqueue(orderNode);
                        }
                        ExchangeOutput.println("T "+stock.timeFromStart()+" "+qty+" "+actionString);
                        //System.out.println("transacted");
                    }
                }
                else
                {
                    buy.enqueue(orderNode);
                    ExchangeOutput.println("P "+stock.timeFromStart()+" 0 "+actionString);
                    //System.out.println("added to buy list");
                }
            }

            if (type.equals("sell"))
            {
                sellingPrice = Integer.parseInt(orderNode.fields[6]);
                temp = buy.head;
                for (i = 0; i < buy.size(); i++)
                {
                    int t=Integer.parseInt(temp.fields[0]);
                    int tExp=Integer.parseInt(temp.fields[2]);
                    if(stock.timeFromStart()>t+tExp)
                    {
                        temp=temp.next;
                        continue;
                    }
                    buyingPrice = Integer.parseInt(temp.fields[6]);
                    spread = buyingPrice - sellingPrice;
                    if ((temp.fields[5].equals(stockName)) && (spread >= 0))
                    {
                        partial2 = temp.fields[7].equals("T");
                        qty2 = Integer.parseInt(temp.fields[4]);
                        if ((!partial1) && (!partial2))
                        {
                            if (qty1 == qty2)
                                record.put(i, spread * qty2);
                        }
                        if ((!partial1) && (partial2))
                        {
                            if (qty1 <= qty2)
                                record.put(i, spread * qty1);
                        }
                        if ((partial1) && (!partial2))
                        {
                            if (qty1 >= qty2)
                                record.put(i, spread * qty2);
                        }
                        if ((partial1) && (partial2))
                            record.put(i, spread * Math.min(qty1, qty2));
                    }
                    temp = temp.next;
                }
                Iterator it = record.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry pair = (Map.Entry) it.next();
                    profit = Math.max(profit, (int) pair.getValue());
                }
                it = record.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (profit == (int) pair.getValue())
                    {
                        position = (int) pair.getKey();
                        break;
                    }
                }
                temp = buy.head;
                for (i = 1; i < position; i++)
                    temp = temp.next;
                if (temp != null)
                {
                    if(record.isEmpty())
                    {
                        sell.enqueue(orderNode);
                        ExchangeOutput.println("S "+stock.timeFromStart()+" 0 "+actionString);
                        //System.out.println("added to sell list");
                    }
                    else
                    {
                        qty2 = Integer.parseInt(temp.fields[4]);
                        String qty=Integer.toString(Math.min(qty1, qty2));
                        if (Math.min(qty1, qty2) == qty1)
                        {
                            orderNode.fields[4] = "0";
                            temp.fields[4] = Integer.toString(qty2 - qty1);
                        }
                        else
                        {
                            orderNode.fields[4] = Integer.toString(qty1 - qty2);
                            temp.fields[4] = "0";
                            sell.enqueue(orderNode);
                        }
                        ExchangeOutput.println("T "+stock.timeFromStart()+" "+qty+" "+actionString);
                        //System.out.println("transacted");
                    }
                }
                else
                {
                    sell.enqueue(orderNode);
                    ExchangeOutput.println("S "+stock.timeFromStart()+" 0 "+actionString);
                    //System.out.println("added to sell list");
                }
            }
        }
        catch(Exception e){}
        return profit;
    }
}
