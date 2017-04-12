package commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandExec {
	 public static void exec(String command) {
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
 }

}
