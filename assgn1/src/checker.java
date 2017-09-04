/**
 * Created by hp 15 on 8/19/2016.
 */
import java.io.*;

public class checker
{
    public static void main (String args[])
    {
        BufferedReader br = null;
        stock r = new stock();
        try
        {
            String actionString;
            br = new BufferedReader(new FileReader("input1.txt"));
            while ((actionString = br.readLine()) != null)
            {
                r.performAction(actionString);
            }
            //this is the only change in checker
            r.cleanUp();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(br != null)
                    br.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
