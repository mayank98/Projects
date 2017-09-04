/**
 * Created by hp 15 on 9/26/2016.
 */
public class WordEntry
{
    String word;
    MyLinkedList <Position> allPositionsForThisWord=new MyLinkedList<>();
    WordEntry(String str) {word=str;}

    void addPosition(Position position) {allPositionsForThisWord.add(position);}

    void addPositions(MyLinkedList <Position> positions)
    {
        Node <Position> temp=positions.head;
        while(temp!=null)
        {
            allPositionsForThisWord.add(temp.data);
            temp=temp.next;
        }
    }

    MyLinkedList <Position> getAllPositionsForThisWord() {return allPositionsForThisWord;}
}
