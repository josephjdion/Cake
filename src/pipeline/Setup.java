package pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * This is the class used to setup the environment for the users local machine
 * @author Joe
 *
 */
public class Setup {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SaveEnvironment s = new SaveEnvironment();
		EnvironmentInfo infoToSave =  new EnvironmentInfo();
		try {
			sop("Welcome to the setup for cake!");
			sop("Please enter the directory that contains BBTools: ");
			infoToSave.setBBToolsDir(br.readLine());
			sop("Please enter the directory that contains mothur: ");
			infoToSave.setMothurDir(br.readLine());
			sop("Please enter your machine's number of virtualized cores: ");
			infoToSave.setNumOfProcessors(br.readLine());
			sop("Please enter the verison number of silva you are working with");
			infoToSave.setSilva(br.readLine());
			sop("Thank you for properly setting up your environment!");
			s.saveEnvironment(infoToSave);
			sop("Please use the Cake jar now");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sop(Object obj) {
		System.out.println(obj.toString());
	}
}
