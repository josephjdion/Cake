package pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Setup {
	private static String bbString;

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SaveEnvironment s = new SaveEnvironment();
		try {
			sop("Welcome to the setup for cake!");
			sop("Please enter the directory that contains BBTools: ");
			s.setBBToolsDir(br.readLine());
			sop("Please enter the directory that contains mothur: ");
			s.setMothurDir(br.readLine());
			sop("Please enter your machine's number of virtualized cores: ");
			s.setNumOfProcessors(Integer.parseInt(br.readLine()));
			sop("Please enter the verison number of silva you are working with");
			s.setSilvaRefVersion(Integer.parseInt(br.readLine()));
			sop("Thank you for properly setting up your environment!");
			sop("Please use the Cake jar now");
			s.saveEnvironment();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sop(Object obj) {
		System.out.println(obj.toString());
	}
}
