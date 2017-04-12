package pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is currently the main entry point of the application. It takes basic
 * console input to analyze two fastq files. It will be expanded in the future
 * to be able to save environment conditions to disk.
 * 
 * @author Joe
 *
 */
public class Cake {
	private Scanner scanner = new Scanner(System.in);
	private Commands com;
	private EnvironmentInfo EI;
	private ArrayList<File> files = new ArrayList<File>();
	private  File inputDir;
	/**
	 * Sets up the environment conditions and specify fastq files
	 */
	private void setup() {
		sop("Welcome to cake setup");
		SaveEnvironment save =  new SaveEnvironment();
		EI = save.loadEnvironmentInfo();
		sop("Please enter the directory containing the fastq files you wish to use: ");
		inputDir = new File(sin());
	}

	private static void sop(String str) {
		System.out.println(str);
		System.out.flush();
	}

	private String sin() {
		return scanner.nextLine();
	}
	
	// Main entry point for simplified application
	
	public static void main(String[] args) {
		Cake c = new Cake();
		c.setup();
		Pipeline p = new Pipeline(c.inputDir, c.EI);
		p.execute();

	}
}
