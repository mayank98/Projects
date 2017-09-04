/**
 * Created by hp 15 on 10/17/2016.
 */
import java.util.*;

public class MySort
{
    ArrayList <SearchResult> sortThisList(MySet <SearchResult> listOfSortableEntries)
    {
        int n=listOfSortableEntries.size();
        SearchResult arr[]=new SearchResult[n];
        Node <SearchResult> temp = listOfSortableEntries.list.head;
        int i=0,j;

        while(temp!=null)
        {
            arr[i]=temp.data;
            temp = temp.next;
            i++;
        }

        for(i=0;i<n-1;i++)
        {
            for(j=i+1;j<n;j++)
            {
                if(arr[j].compareTo(arr[i])>0)
                {
                    SearchResult temp2=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp2;
                }
            }
        }

        return new ArrayList<>(Arrays.asList(arr));
    }
}
