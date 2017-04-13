package commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * This class is responsible for executing commands in the console
 * @author Joe
 */
public class CommandExec {
		/**
		 * Executes given commands
		 * @param Text to use as console command
		 * @return Text output
		 */
		 public static String exec(String command) {
				StringBuffer output = new StringBuffer();
				Process p;
				try {
					p = Runtime.getRuntime().exec(command);
					p.waitFor();
					BufferedReader reader =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = "";
					while ((line = reader.readLine())!= null) {
						output.append(line + "\n");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(output.toString());
				return output.toString();
			}
}