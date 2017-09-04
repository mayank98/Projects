/**
 * Created by hp 15 on 9/26/2016.
 */
class Node<X>
{
    X data;
    Node <X> next;
    Node(X val)
    {
        data=val;
        next=null;
    }
}

public class MyLinkedList<X>
{
    Node <X> head;
    MyLinkedList() {head=null;}

    int size()
    {
        if(head==null)
            return 0;
        Node temp=head;
        int len=1;
        while(temp.next!=null)
        {
            len++;
            temp=temp.next;
        }
        return len;
    }

    void add(X element)
    {
        Node <X> node=new Node<>(element);
        if(head==null)
            head=node;
        else
        {
            Node temp=head;
            while(temp.next!=null)
                temp=temp.next;
            temp.next=node;
        }
    }

    boolean contains(X element)
    {
        Node temp=head;
        String str="test";
        if(element.getClass().equals(str.getClass()))
        {
            while(temp!=null)
            {
                if(temp.data.equals(element))
                    return true;
                temp=temp.next;
            }
        }
        else
        {
            while(temp!=null)
            {
                if(temp.data==element)
                    return true;
                temp=temp.next;
            }
        }
        return false;
    }
}
