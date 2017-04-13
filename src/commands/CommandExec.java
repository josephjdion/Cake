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
			 try {
					Process p = Runtime.getRuntime().exec(command);

					// p.waitFor();
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((reader.readLine()) != null) {
					}
					p.waitFor();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 return "Return text currently no implemented";
			}
}