/**
 * Created by admin on 3/9/2016.
 */
public class MobilePhoneSet
{
    Myset set=new Myset();

    public MobilePhoneSet Union(MobilePhoneSet a)
    {
        MobilePhoneSet mpSet=new MobilePhoneSet();
        mpSet.set=set.Union(a.set);
        return mpSet;
    }
}
