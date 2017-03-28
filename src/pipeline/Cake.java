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

	public static void main(String[] args) {
		Cake c = new Cake();

		c.setup();
		c.com = new Commands(c.EI, c.files);
		CommandList.executeStandardAnalysis(c.com);

	}

	private void setup() {
		sop("Welcome to cake setup");

		sop("Please enter BBTools directory");
		File bbToolsDir = new File(this.sin());

		sop("Please enter mothur directory");
		File mothurDir = new File(this.sin());

		sop("Please enter first Fastq full path");
		File fq1 = new File(this.sin());

		sop("Please enter second Fastq full path");
		File fq2 = new File((this.sin()));

		this.EI = new EnviromentInfo(bbToolsDir, mothurDir);
		this.files.add(fq1);
		this.files.add(fq2);

	}

	private static void sop(String str) {
		System.out.println(str);
		System.out.flush();
	}

	private String sin() {
		return scanner.nextLine();
	}

	private Scanner scanner = new Scanner(System.in);
	Commands com;
	EnviromentInfo EI;
	ArrayList<File> files = new ArrayList<File>();

}
