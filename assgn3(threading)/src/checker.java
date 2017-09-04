/**
 * Created by admin on 3/9/2016.
 */
import java.io.*;
import java.util.*;

public class checker
{
    static long startTime=System.currentTimeMillis();

    static long timeFromStart()
    {
        return (System.currentTimeMillis()-startTime)/1000;
    }

    public static void main (String args [])
    {
        RoutingMapTree r = new RoutingMapTree();
        BufferedReader br=null;
        Thread.currentThread().setName("CentralServer");
        try {
            String actionString;
            br = new BufferedReader(new FileReader("actions(threading).txt"));

            while ((actionString = br.readLine()) != null) {
                Scanner in=new Scanner(actionString);
                int tStart=in.nextInt();
                while (timeFromStart()<tStart);
                r.performAction(actionString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
