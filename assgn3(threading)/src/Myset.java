/**
 * Created by admin on 3/9/2016.
 */
import java.util.*;

public class Myset
{
    LinkedList <Object> elementList=new LinkedList<>();

    public boolean IsEmpty() {return (elementList.size()==0);}

    public boolean IsMember(Object O) {return elementList.contains(O);}

    public void Insert(Object O)
    {
        if(!elementList.contains(O))
            elementList.add(O);
    }

    public void Delete(Object O)
    {
        if(!elementList.contains(O))
        {
            //throw exception
            return;
        }
        elementList.remove(O);
    }

    public int size() {return elementList.size();}

    public Object get(int i) {return elementList.get(i);}

    public Myset Union(Myset a)
    {
        Myset c=new Myset();
        c.elementList=elementList;
        int n=a.elementList.size();
        int i;
        for(i=0;i<n;i++)
        {
            Object O=a.elementList.get(i);
            if(!c.elementList.contains(O))
                c.elementList.add(O);
        }
        return c;
    }

    public Myset Intersection(Myset a)
    {
        Myset c=new Myset();
        int n=elementList.size();
        int i;
        for(i=0;i<n;i++)
        {
            Object O=elementList.get(i);
            if(a.elementList.contains(O))
                c.elementList.add(O);
        }
        return c;
    }
}
