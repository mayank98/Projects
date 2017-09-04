/**
 * Created by admin on 3/9/2016.
 */
import java.util.*;

public class Exchange
{
    int identifier;
    LinkedList <Exchange> childrenList=new LinkedList<>();
    Exchange parent;
    MobilePhoneSet regMobileSet=new MobilePhoneSet();
    Exchange(){}

    Exchange(int number) {identifier = number;}

    public Exchange parent() {return parent;}

    public Exchange child(int i) {return childrenList.get(i);}

    public boolean isRoot() {return (parent==null);}

    public int numChildren() {return childrenList.size();}

    public boolean isLeaf()
    {
        if(childrenList.size()==0)
            return true;
        return false;
    }

    public void dfs(Exchange e,RoutingMapTree tree)
    {
        tree.listOfExchanges.list.add(e);
        int i,n=e.childrenList.size();
        for(i=0;i<n;i++)
            dfs(e.childrenList.get(i),tree);
    }

    public RoutingMapTree subtree(int i)
    {
        RoutingMapTree tree=new RoutingMapTree(childrenList.get(i));
        dfs(childrenList.get(i),tree);
        return tree;
    }

    public MobilePhoneSet residentSet()
    {
        int n=childrenList.size();
        int i;
        for(i=0;i<n;i++)
        {
            MobilePhoneSet temp=childrenList.get(i).residentSet();
            regMobileSet=regMobileSet.Union(temp);
        }
        return regMobileSet;
    }
}
