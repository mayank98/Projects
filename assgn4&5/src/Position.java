/**
 * Created by hp 15 on 9/26/2016.
 */
public class Position
{
    PageEntry page;
    int wordIndex;

    Position(PageEntry p,int index)
    {
        page=p;
        wordIndex=index;
    }

    PageEntry getPageEntry() {return page;}
    int getWordIndex() {return wordIndex;}
}
