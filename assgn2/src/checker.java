/**
 * Created by admin on 3/9/2016.
 */
import java.io.*;

public class checker
{
    public static void main ( String args [])
    {
        BufferedReader br = null;
        RoutingMapTree r = new RoutingMapTree();

        try {
            String actionString;
            br = new BufferedReader(new FileReader("actions.txt"));

            while ((actionString = br.readLine()) != null) {
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
