/**
 * Created by hp 15 on 9/26/2016.
 */
public class MyHashTable
{
    int SIZE=2011;
    int seed=131;
    MyLinkedList <WordEntry> chainingList[]=new MyLinkedList[SIZE];
    MyHashTable()
    {
        for(int i=0;i<SIZE;i++)
            chainingList[i]=new MyLinkedList<>();
    }

    private int getHashIndex(String str)
    {
        int i;
        int hash = 0;
        for(i = 0; i < str.length(); i++)
            hash=((hash*seed)+str.charAt(i))%SIZE;
        return hash;
    }

    int index(String str) {return getHashIndex(str);}

    void addPositionsForWord(WordEntry w)
    {
        int hashVal=getHashIndex(w.word);
        Node <WordEntry> temp=chainingList[hashVal].head;
        while(temp!=null)
        {
            if(temp.data.word.equals(w.word))
            {
                temp.data.addPositions(w.allPositionsForThisWord);
                break;
            }
            temp=temp.next;
        }
        if(temp==null)
        {
            WordEntry temp1 = new WordEntry(w.word);
            temp1.addPositions(w.getAllPositionsForThisWord());
            chainingList[hashVal].add(temp1);
        }

    }
}
