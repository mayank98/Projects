/**
 * Created by hp 15 on 10/17/2016.
 */
public class SearchResult implements Comparable<SearchResult>
{
    PageEntry page;
    float relevance;

    public SearchResult(PageEntry p,float r)
    {
        page=p;
        relevance=r;
    }

    public PageEntry getPageEntry() {return page;}
    public float getRelevance() {return relevance;}

    public int compareTo(SearchResult otherObject)
    {
        if(this.relevance>otherObject.relevance)
            return 1;
        if(this.relevance<otherObject.relevance)
            return -1;
        return 0;
    }
}
