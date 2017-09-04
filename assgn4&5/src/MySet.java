/**
 * Created by hp 15 on 9/26/2016.
 */
public class MySet<X>
{
    MyLinkedList <X> list=new MyLinkedList<X>();

    int size() {return list.size();}

    void addElement(X element)
    {
        if(!list.contains(element))
            list.add(element);
    }

    MySet <X> union(MySet <X> otherSet)
    {
        MySet <X> result=new MySet<X>();
        result.list=this.list;
        Node temp=otherSet.list.head;
        while(temp!=null)
        {
            result.addElement((X)temp.data);
            temp=temp.next;
        }
        return result;
    }

    MySet <X> intersection(MySet <X> otherSet)
    {
        MySet <X> result=new MySet<X>();
        Node temp=otherSet.list.head;
        while(temp!=null)
        {
            if(this.list.contains((X)temp.data))
                result.addElement((X)temp.data);
            temp=temp.next;
        }
        return result;
    }
}
