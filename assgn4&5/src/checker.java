import java.io.*;

public class checker
{
	public static void main ( String args [])
	{
		BufferedReader br = null;
		SearchEngine r = new SearchEngine();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("myNewActions.txt"));

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
