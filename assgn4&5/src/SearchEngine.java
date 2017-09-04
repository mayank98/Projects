/**
 * Created by hp 15 on 9/26/2016.
 */
import java.util.*;

public class SearchEngine
{
    InvertedPageIndex docs;
	SearchEngine() {docs=new InvertedPageIndex();}

	public void performAction(String actionMessage)
    {
        Scanner in=new Scanner(actionMessage);
        String query=in.next();
        String x=in.next();
        x=x.toLowerCase();
        String y;
        if(query.equals("addPage"))
        {
            Node <PageEntry> temp1=docs.pageEntries.head;
            while(temp1!=null)
            {
                if(temp1.data.pageName.equals(x))
                    break;
                temp1=temp1.next;
            }
            if(temp1==null)
            {
                PageEntry p=new PageEntry(x);
                docs.addPage(p);
                MyLinkedList <WordEntry> ls=p.getPageIndex().getWordEntries();
                Node <WordEntry> temp=ls.head;
                while(temp!=null)
                {
                    docs.hashTable.addPositionsForWord(temp.data);
                    temp=temp.next;
                }
            }
        }

        if(query.equals("queryFindPagesWhichContainWord"))
        {
            MySet <PageEntry> pages=docs.getPagesWhichContainWord(x);
            System.out.print(actionMessage+": ");
            Node <PageEntry> temp = pages.list.head;
            if(temp==null)
                System.out.print("no webpage contains word "+x);
            while(temp!=null)
            {
                System.out.print(temp.data.pageName+", ");
                temp=temp.next;
            }
            System.out.println();
        }

        if(query.equals("queryFindPagesWhichContainAllWords"))
        {
            String misc[]=actionMessage.split("\\s+");
            String words[]=Arrays.copyOfRange(misc,1,misc.length);
            int i;
            MySet <PageEntry> pages=docs.getPagesWhichContainWord(words[0]);
            for(i=0;i<words.length;i++)
                pages=pages.intersection(docs.getPagesWhichContainWord(words[i]));
            System.out.print(actionMessage+": ");
            Node <PageEntry> temp = pages.list.head;
            MySet <SearchResult> A=new MySet<>();
            if(temp==null)
                System.out.print("no webpage contains all the words");
            while(temp!=null)
            {
                A.addElement(new SearchResult(temp.data,temp.data.getRelevanceOfPage(words,false)));
                temp=temp.next;
            }
            ArrayList <SearchResult> sortedList=new MySort().sortThisList(A);
            int n=sortedList.size();
            for(i=0;i<n;i++)
                System.out.print(sortedList.get(i).getPageEntry().pageName+", ");
            System.out.println();
        }

        if(query.equals("queryFindPagesWhichContainAnyOfTheseWords"))
        {
            String misc[]=actionMessage.split("\\s+");
            String words[]=Arrays.copyOfRange(misc,1,misc.length);
            int i;
            MySet <PageEntry> pages=docs.getPagesWhichContainWord(words[0]);
            for(i=0;i<words.length;i++)
                pages=pages.union(docs.getPagesWhichContainWord(words[i]));
            System.out.print(actionMessage+": ");
            Node <PageEntry> temp = pages.list.head;
            MySet <SearchResult> A=new MySet<>();
            if(temp==null)
                System.out.print("no webpage contains any of these words");
            while(temp!=null)
            {
                A.addElement(new SearchResult(temp.data,temp.data.getRelevanceOfPage(words,false)));
                temp=temp.next;
            }
            ArrayList <SearchResult> sortedList=new MySort().sortThisList(A);
            int n=sortedList.size();
            for(i=0;i<n;i++)
                System.out.print(sortedList.get(i).getPageEntry().pageName+", ");
                //System.out.print(sortedList.get(i).getPageEntry().pageName+sortedList.get(i).getRelevance()+", ");
            System.out.println();
        }

        if(query.equals("queryFindPositionsOfWordInAPage"))
        {
            y=in.next();
            Node <PageEntry> temp=docs.pageEntries.head;
            while(temp!=null)
            {
                if(temp.data.pageName.equals(y))
                    break;
                temp=temp.next;
            }
            System.out.print(actionMessage+": ");
            ///
            //String a[]={x};
            //System.out.print(" relevance="+temp.data.getRelevanceOfPage(a,false)+" ");
            if(temp==null)
                System.out.println("webpage "+y+" doesn't exist");
            else
            {
                Node <WordEntry> temp2 = temp.data.getPageIndex().getWordEntries().head;
                while (temp2 != null)
                {
                    if (temp2.data.word.equals(x))
                        break;
                    temp2 = temp2.next;
                }
                if (temp2 == null)
                    System.out.println("webpage " + y + " doesn't contain word " + x);
                else
                {
                    Node<Position> temp3 = temp2.data.getAllPositionsForThisWord().head;
                    while (temp3 != null)
                    {
                        System.out.print(temp3.data.getWordIndex() + ", ");
                        temp3 = temp3.next;
                    }
                    System.out.println();
                }
            }
        }
    }
}