/**
 * Created by admin on 3/9/2016.
 */
public class MobilePhone implements Runnable
{
    int identifier;
    boolean switchedOn;
    Exchange Location;

    //variables required for threading
    String query;
    boolean busy;
    RoutingMapTree tree;
    int a,b;
    int tStart,tEnd;

    MobilePhone()
    {
        switchedOn=true;
        busy=false;
    }

    MobilePhone(int number)
    {
        identifier=number;
        switchedOn=true;
        busy=false;
    }

    MobilePhone(RoutingMapTree t,int x,int y,String str,int t1,int t2)
    {
        tree=t;
        a=x;
        b=y;
        query=str;
        tStart=t1;
        tEnd=t2;
    }

    public int number() {return identifier;}

    public boolean status() {return switchedOn;}

    public void switchOn() {switchedOn=true;}

    public void switchOff() {switchedOn=false;}

    public Exchange location() throws Exception
    {
        if(!status())
        {
            //System.out.println("specified MobilePhone is switched off, so can't tell location.");
            throw new Exception();
        }
        else
        {
            return Location;
        }
    }

    public void run()
    {
        if(query.equals("switchOnMobile"))
        {
            //System.out.println(Thread.currentThread().getName() + " start");
            try
            {
                System.out.print("switchOnMobile " + a + " " + b + ": ");
                tree.switchOnMobile(a, b);
                System.out.println();
            }
            catch (Exception err)
            {
                System.out.println("no Exchange with identifier " + b + " is present.");
            }
            //System.out.println(Thread.currentThread().getName() + " end");
        }

        if(query.equals("switchOffMobile"))
        {
            try
            {
                System.out.print("switchOffMobile "+a+": ");
                tree.switchOffMobile(a);
                System.out.println();
            }
            catch (Exception err)
            {
                System.out.println("no MobilePhone with identifier "+a+" is present.");
            }
        }

        if(query.equals("movePhone"))
        {
            MobilePhoneSet M=tree.root.residentSet();
            int i,j,n=M.set.size();
            MobilePhone mp=new MobilePhone();
            for(i=0;i<n;i++)
            {
                mp=(MobilePhone) M.set.get(i);
                if(mp.number()==a)
                    break;
            }
            n=tree.listOfExchanges.list.size();
            Exchange e=new Exchange();
            for(j=0;j<n;j++)
            {
                e=tree.listOfExchanges.list.get(j);
                if(e.identifier==b)
                    break;
            }
            try
            {
                System.out.print("movePhone "+a+" "+b+": ");
                if((i==n)||(j==n))
                {
                    throw new Exception();
                }
                tree.movePhone(mp,e);
                System.out.println();
            }
            catch (Exception err)
            {
                System.out.println("the specified MobilePhone doesn't exist or is switched off and/or " +
                        "the specified Exchange doesn't exist or isn't a base station.");
            }
        }

        if(query.equals("queryFindCallPath"))
        {
            //System.out.println(Thread.currentThread().getName() + " start");
            long callStart=System.currentTimeMillis();
            MobilePhoneSet M=tree.root.residentSet();
            int i,j,n=M.set.size();
            MobilePhone mp1=new MobilePhone();
            MobilePhone mp2=new MobilePhone();
            for(i=0;i<n;i++)
            {
                mp1=(MobilePhone) M.set.get(i);
                if(mp1.number()==a) {
                    break;
                }
            }
            for(j=0;j<n;j++)
            {
                mp2=(MobilePhone) M.set.get(j);
                if(mp2.number()==b) {
                    break;
                }
            }
            System.out.print("queryFindCallPath "+a+" "+b+": ");
            try
            {
                if((i==n)||(j==n)||(!mp1.status())||(!mp2.status()))
                {
                    throw new Exception();
                }
                if(mp1.busy)
                {
                    System.out.println("invalid query. the phone by which you are calling is already in use.");
                    return;
                }
                if(!mp2.busy)
                {
                    System.out.println("call connection successful. both phones are now busy.");
                    while((System.currentTimeMillis()-callStart)/1000<tEnd)
                    {
                        mp1.busy=true;
                        mp2.busy=true;
                    }
                    mp1.busy=false;
                    mp2.busy=false;
                }
                else
                {
                    System.out.println("call connection unsuccessful. the other phone is busy right now, please try again later.");
                    mp1.busy=false;
                }
            }
            catch (Exception err)
            {
                System.out.println("the specified MobilePhone doesn't exist or is switched off.");
            }
            //System.out.println(Thread.currentThread().getName() + " end");
        }
    }
}
