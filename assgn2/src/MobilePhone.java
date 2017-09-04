/**
 * Created by admin on 3/9/2016.
 */
public class MobilePhone
{
    int identifier;
    boolean switchedOn;
    Exchange Location;

    MobilePhone(){switchedOn=true;}
    MobilePhone(int number)
    {
        identifier=number;
        switchedOn=true;
    }

    public int number() {return identifier;}

    public boolean status() {return switchedOn;}

    public void switchOn() {switchedOn=true;}

    public void switchOff() {switchedOn=false;}

    public Exchange location() throws Exception
    {
        if(!status())
        {
            //System.out.println("specified MobilePhone is switched off, so can't tell location.");
            throw new Exception();
        }
        else
        {
            return Location;
        }
    }
}
