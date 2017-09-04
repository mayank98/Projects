/**
 * Created by admin on 3/9/2016.
 */
import java.util.*;


public class RoutingMapTree
{
    Exchange root;
    ExchangeList listOfExchanges=new ExchangeList();

    public RoutingMapTree(Exchange e) {root=e;}

    public RoutingMapTree()
    {
        root=new Exchange(0);
        listOfExchanges.list.add(root);
        root.parent=null;
    }

    public boolean containsNode(Exchange a) {return listOfExchanges.list.contains(a);}

    public void switchOn(MobilePhone a, Exchange b) throws Exception
    {
        int num=a.number();
        int id=b.identifier;
        MobilePhoneSet M=root.residentSet();
        int n=listOfExchanges.list.size();
        Exchange p=new Exchange();
        int i;
        for(i=0;i<n;i++)
        {
            p=listOfExchanges.list.get(i);
            if(p.identifier==id)
                break;
        }
        if(i==n)
        {
            throw new Exception();
        }
        else
        {
            n=p.regMobileSet.set.size();
            MobilePhone m=new MobilePhone();
            for(i=0;i<n;i++)
            {
                m=(MobilePhone)p.regMobileSet.set.get(i);
                if(m.number()==num)
                    break;
            }
            if(i<n)
                m.switchOn();
            else
            {
                m=new MobilePhone(num);
                p.regMobileSet.set.Insert(m);
            }
        }
    }

    public void switchOnMobile(int a,int b) throws Exception
    {
        MobilePhoneSet M=root.residentSet();
        int n=listOfExchanges.list.size();
        Exchange p=new Exchange();
        int i;
        for(i=0;i<n;i++)
        {
            p=listOfExchanges.list.get(i);
            if(p.identifier==b)
                break;
        }
        if(i==n)
        {
            throw new Exception();
        }
        else
        {
            n=p.regMobileSet.set.size();
            MobilePhone m=new MobilePhone();
            for(i=0;i<n;i++)
            {
                m=(MobilePhone)p.regMobileSet.set.get(i);
                if(m.number()==a)
                    break;
            }
            if(i<n)
                m.switchOn();
            else
            {
                m=new MobilePhone(a);
                p.regMobileSet.set.Insert(m);
            }
            m.Location=p;
        }
    }

    public void switchOff(MobilePhone a) throws Exception
    {
        int id=a.number();
        MobilePhoneSet M=root.residentSet();
        int n=M.set.size();
        int i;
        MobilePhone mp=new MobilePhone();
        for(i=0;i<n;i++)
        {
            mp=(MobilePhone)M.set.get(i);
            if(mp.identifier==id)
                break;
        }
        if(i==n)
        {
            throw new Exception();
        }
        else
        {
            ListIterator it=listOfExchanges.list.listIterator();
            while(it.hasNext())
            {
                Exchange e=(Exchange)it.next();
                if(e.isLeaf())
                {
                    if(e.regMobileSet.set.IsMember(mp))
                    {
                        mp.switchOff();
                        break;
                    }
                }
            }
        }
    }

    public void switchOffMobile(int a) throws Exception
    {
        MobilePhoneSet M=root.residentSet();
        int n=M.set.size();
        int i;
        MobilePhone mp=new MobilePhone();
        for(i=0;i<n;i++)
        {
            mp=(MobilePhone)M.set.get(i);
            if(mp.identifier==a)
                break;
        }
        if(i==n)
        {
            throw new Exception();
        }
        else
        {
            ListIterator it=listOfExchanges.list.listIterator();
            while(it.hasNext())
            {
                Exchange e=(Exchange)it.next();
                if(e.isLeaf()&&(e.regMobileSet.set.IsMember(mp)))
                {
                    mp.switchOff();
                    break;
                }
            }
        }
    }

    public void addExchange(int a,int b) throws Exception
    {
        int n=listOfExchanges.list.size();
        Exchange p=new Exchange();
        int i;
        for(i=0;i<n;i++)
        {
            p=listOfExchanges.list.get(i);
            if(p.identifier==a) {
                break;
            }
        }
        if(i==n)
        {
            throw new Exception();
        }
        Exchange e=new Exchange(b);
        e.parent=p;
        p.childrenList.add(e);
        listOfExchanges.list.add(e);
    }

    //assignment 3 part begin
    public Exchange findPhone(MobilePhone m) throws Exception
    {
        MobilePhoneSet M=root.residentSet();
        if(!m.status())
            throw new Exception();
        int i,n=listOfExchanges.list.size();
        Exchange e=new Exchange();
        for(i=0;i<n;i++)
        {
            e=listOfExchanges.list.get(i);
            if(e.isLeaf()&&e.regMobileSet.set.IsMember(m))
                break;
        }
        return e;
    }

    public Exchange lowestRouter(Exchange a,Exchange b)
    {
        while(true)
        {
            Exchange c=a.parent;
            if(c==null)
                return root;
            int i=c.childrenList.indexOf(a);
            RoutingMapTree t=c.subtree(i);
            if(t.listOfExchanges.list.contains(b))
                break;
            a=a.parent;
        }
        return a;
    }

    public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws Exception
    {
        ExchangeList path=new ExchangeList();
        Exchange commonAncestor=lowestRouter(findPhone(a),findPhone(b));
        Exchange initialEx=findPhone(a);
        Exchange finalEx=findPhone(b);
        while(initialEx.identifier!=commonAncestor.identifier)
        {
            path.list.add(initialEx);
            initialEx=initialEx.parent;
        }
        path.list.add(commonAncestor);
        ExchangeList revPath=new ExchangeList();
        while(finalEx.identifier!=commonAncestor.identifier)
        {
            revPath.list.add(finalEx);
            finalEx=finalEx.parent;
        }
        Collections.reverse(revPath.list);
        path.list.addAll(revPath.list);
        return path;
    }

    public void movePhone(MobilePhone a, Exchange b) throws Exception
    {
        MobilePhoneSet M=root.residentSet();
        if((!b.isLeaf())||(!a.status()))
            throw new Exception();
        Exchange e=findPhone(a);
        e.regMobileSet.set.Delete(a);
        b.regMobileSet.set.Insert(a);
        M=root.residentSet();
    }
    //assignment 3 part end

    public void performAction(String actionMessage)
    {
        Scanner in=new Scanner(actionMessage);
        String str=in.next();
        int a,b;
        //assignment 2 start
        if(str.equals("addExchange"))
        {
            a=in.nextInt();
            b=in.nextInt();
            try
            {
                addExchange(a,b);
            }
            catch (Exception err)
            {
                System.out.print("addExchange "+a+" "+b+": ");
                System.out.println("no Exchange with identifier "+a+" is present.");
            }
        }

        if(str.equals("switchOnMobile"))
        {
            a=in.nextInt();
            b=in.nextInt();
            try
            {
                switchOnMobile(a,b);
            }
            catch (Exception err)
            {
                System.out.print("switchOnMobile "+a+" "+b+": ");
                System.out.println("no Exchange with identifier "+b+" is present.");
            }
        }

        if(str.equals("switchOffMobile"))
        {
            a=in.nextInt();
            try
            {
                switchOffMobile(a);
            }
            catch (Exception err)
            {
                System.out.print("switchOffMobile "+a+": ");
                System.out.println("no MobilePhone with identifier "+a+" is present.");
            }
        }

        if(str.equals("queryNthChild"))
        {
            a=in.nextInt();
            b=in.nextInt();
            int i,n=listOfExchanges.list.size();
            Exchange p=new Exchange();
            for(i=0;i<n;i++)
            {
                p=listOfExchanges.list.get(i);
                if(p.identifier==a)
                    break;
            }
            System.out.print("queryNthChild "+a+" "+b+": ");
            System.out.println(p.child(b).identifier);
            /*
            RoutingMapTree t=p.subtree(b);
            n=t.listOfExchanges.list.size();
            for(i=0;i<n;i++)
            {
                System.out.print(t.listOfExchanges.list.get(i).identifier+" ");
            }
            System.out.println();
            */
            /*
            RoutingMapTree t=listOfExchanges.list.get(0).subtree(1);
            n=t.listOfExchanges.list.size();
            for(i=0;i<n;i++)
            {
                System.out.print(t.listOfExchanges.list.get(i).identifier+" ");
            }
            System.out.println();
            */
        }

        if(str.equals("queryMobilePhoneSet"))
        {
            a=in.nextInt();
            MobilePhoneSet M=root.residentSet();
            Exchange e;
            int n=listOfExchanges.list.size();
            int i,j;
            for(i=0;i<n;i++)
            {
                e=listOfExchanges.list.get(i);
                if(e.identifier==a)
                {
                    int n2 = e.regMobileSet.set.size();
                    System.out.print("queryMobilePhoneSet "+a+": ");
                    for(j=0;j<n2;j++)
                    {
                        MobilePhone mp=(MobilePhone)e.regMobileSet.set.get(j);
                        if(mp.status())
                            System.out.print(mp.identifier+", ");
                    }
                    System.out.println();
                }
            }
        }
        //assignment 2 end

        //assignment 3 start
        if(str.equals("queryFindPhone"))
        {
            a=in.nextInt();
            MobilePhoneSet M=root.residentSet();
            int n=M.set.size();
            int i;
            MobilePhone mp=new MobilePhone();
            for(i=0;i<n;i++)
            {
                mp=(MobilePhone)M.set.get(i);
                if(mp.number()==a)
                    break;
            }
            try
            {
                System.out.print("queryFindPhone "+a+": ");
                if(i == n)
                    throw new Exception();
                System.out.println(findPhone(mp).identifier);
            }
            catch(Exception err)
            {
                System.out.println("the specified MobilePhone doesn't exist or is switched off.");
            }
        }

        if(str.equals("queryLowestRouter"))
        {
            a=in.nextInt();
            b=in.nextInt();
            int i,j,n=listOfExchanges.list.size();
            Exchange e1=new Exchange();
            Exchange e2=new Exchange();
            System.out.print("queryLowestRouter "+a+" "+b+": ");
            for(i=0;i<n;i++)
            {
                if(listOfExchanges.list.get(i).identifier==a)
                {
                    e1=listOfExchanges.list.get(i);
                    break;
                }
            }
            for(j=0;j<n;j++)
            {
                if(listOfExchanges.list.get(j).identifier==b)
                {
                    e2=listOfExchanges.list.get(j);
                    break;
                }
            }
            try
            {
                if((i==n)||(j==n))
                    throw new Exception();
                System.out.println(lowestRouter(e1,e2).identifier);
            }
            catch (Exception err)
            {
                System.out.println("the specified exchange doesn't exist.");
            }
        }

        if(str.equals("queryFindCallPath"))
        {
            a=in.nextInt();
            b=in.nextInt();
            MobilePhoneSet M=root.residentSet();
            int i,j,n=M.set.size();
            MobilePhone mp1=new MobilePhone();
            MobilePhone mp2=new MobilePhone();
            for(i=0;i<n;i++)
            {
                mp1=(MobilePhone) M.set.get(i);
                if(mp1.number()==a)
                    break;
            }
            for(j=0;j<n;j++)
            {
                mp2=(MobilePhone) M.set.get(j);
                if(mp2.number()==b)
                    break;
            }
            System.out.print("queryFindCallPath "+a+" "+b+": ");
            try
            {
                if((i==n)||(j==n)||(!mp1.status())||(!mp2.status()))
                    throw new Exception();
                ExchangeList ls=routeCall(mp1,mp2);
                n=ls.list.size();
                for(i=0;i<n;i++)
                    System.out.print(ls.list.get(i).identifier+", ");
                System.out.println();
            }
            catch (Exception err)
            {
                System.out.println("the specified MobilePhone doesn't exist or is switched off.");
            }
        }

        if(str.equals("movePhone"))
        {
            a=in.nextInt();
            b=in.nextInt();
            MobilePhoneSet M=root.residentSet();
            int i,j,n=M.set.size();
            MobilePhone mp=new MobilePhone();
            for(i=0;i<n;i++)
            {
                mp=(MobilePhone) M.set.get(i);
                if(mp.number()==a)
                    break;
            }
            n=listOfExchanges.list.size();
            Exchange e=new Exchange();
            for(j=0;j<n;j++)
            {
                e=listOfExchanges.list.get(j);
                if(e.identifier==b)
                    break;
            }
            try
            {
                if((i==n)||(j==n))
                    throw new Exception();
                movePhone(mp,e);
            }
            catch (Exception err)
            {
                System.out.print("movePhone "+a+" "+b+": ");
                System.out.println("the specified MobilePhone doesn't exist or is switched off and/or " +
                        "the specified Exchange doesn't exist or isn't a base station.");
            }
        }
        //assignment 3 end
    }
}