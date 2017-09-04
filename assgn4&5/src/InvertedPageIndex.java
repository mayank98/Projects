/**
 * Created by hp 15 on 9/26/2016.
 */
public class InvertedPageIndex
{
    MyLinkedList <PageEntry> pageEntries=new MyLinkedList<>();
    MyHashTable hashTable=new MyHashTable();

    void addPage(PageEntry p) {pageEntries.add(p);}

    MySet <PageEntry> getPagesWhichContainWord(String str)
    {
        MySet <PageEntry> result=new MySet<>();
        MyLinkedList <WordEntry> ls=hashTable.chainingList[hashTable.index(str)];
        Node <WordEntry> temp=ls.head;
        while(temp!=null)
        {
            WordEntry currWordEntry=temp.data;
            if(currWordEntry.word.equals(str))
            {
                MyLinkedList <Position> currList=currWordEntry.getAllPositionsForThisWord();
                Node <Position> temp2=currList.head;
                while(temp2!=null)
                {
                    result.addElement(temp2.data.getPageEntry());
                    temp2=temp2.next;
                }
            }
            temp=temp.next;
        }
        return result;
    }

    MySet <PageEntry> getPagesWhichContainPhrase(String str[])
    {
        MySet <PageEntry> result=new MySet<>();
        return result;
    }
}
