/**
 * Created by hp 15 on 9/26/2016.
 */
public class PageIndex
{
    MyLinkedList <WordEntry> wordEntries=new MyLinkedList<>();

    void addPositionForWord(String str, Position p)
    {
        Node <WordEntry> temp=wordEntries.head;
        while(temp!=null)
        {
            if(temp.data.word.equals(str))
            {
                temp.data.addPosition(p);
                break;
            }
            temp=temp.next;
        }
        if(temp==null)
        {
            WordEntry tempWordEntry=new WordEntry(str);
            tempWordEntry.addPosition(p);
            wordEntries.add(tempWordEntry);
        }
    }

    MyLinkedList <WordEntry> getWordEntries() {return wordEntries;}
}
