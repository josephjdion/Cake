package pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import util.FastqReader;

public class Commands {

	public Commands(File output, File BBtools, File Mothur) {
		// As of now I am giving the outputDir a generic name.
		// Bad idea for a naming convention given that this is going to be
		// called with multiple files in the same directory
		this.outputDir = new File(output.toString() + "/CakeFiles");
		this.BBtoolsDir = BBtools;
		this.mothurDir = Mothur;
	}

	// =========================================
	// BBTools commands
	// =========================================

	/**
	 * This gets the merge command
	 * 
	 * @param fq1
	 *            First fastq you want to merge
	 * @param fq2
	 *            Second fastq you want to merge
	 * @return returns the command for merge given the specified file name as a
	 *         String
	 */
	public String getMerge(File fq1, File fq2) {
		return this.BBtoolsDir + "/bbmerge.sh" + " in1=" + fq1 + " in2=" + fq2 + " out=" + outputDir
				+ "/00_MERGED.fastq";
	}

	/**
	 * Gets
	 * 
	 * @return returns a 16s trim of a file command as a string
	 */
	public String get16sTrim() {
		return getTrim(quality16s);
	}

	/**
	 * 
	 * @return returns a 18s trim of a file command as a string
	 */
	public String get18sTrim() {
		return getTrim(quality18s);
	}

	/**
	 * Actually returns the command
	 * 
	 * @param qual
	 *            What quality to use 16s or 18s
	 * @return
	 */
	private String getTrim(int qual) {
		return this.BBtoolsDir + "/bbduk.sh" + " in=" + outputDir + "/00_MERGED.fastq" + " out=" + outputDir
				+ "/01_TRIMMED.fastq" + " qtrim=rl" + " trimq=" + qual + " minlen=" + length;
	}

	// Setters for quality
	public void setQuality16s(int quality16s) {
		this.quality16s = quality16s;
	}

	public void setQuality18s(int quality18s) {
		this.quality18s = quality18s;
	}

	public void setLength(int length) {
		this.length = length;
	}

	// =========================================
	// Internal Utility Functions
	// =========================================
	
	/**
	 * Converts the fastq File in the output folder to fasta
	 */
	public void makeFasta() {

		File outputFile = new File(outputDir.toString() + "/02_FASTA.fasta");
		File inputFile = new File(outputDir.toString() + "/01_TRIMMED.fastq");

		try {
			FileReader fr = new FileReader(inputFile);
			FastqReader fqr = new FastqReader(fr);
			fqr.toFasta(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This executes one single string into the console. 
	 * @param command 
	 */
	public void commandExecute(String command) {
		StringBuffer out = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				
				out.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// =========================================
	// Mothur Functions
	// =========================================
	
	
	//./mothur "#unique.seqs(fasta=/Volumes/Work/BioProj/fastq/CakeFiles/02_FASTA.fasta);"
	// "\"Hello\""
	
	public String uniquify() {
		
		 String end = System.getProperty("line.separator");
		
		
		return mothurDir + "/mothur "  
						+ "-q set.dir(input="
						+ outputDir   + ")" + end
						+ "set.dir(output="
						+ outputDir+ ")" + end
						+ "unique.seqs(fasta=02_FASTA.fasta)" + end ;
						
		
		
		/*
		 * //commandline mode
		return 	mothurDir + "/mothur \"" +
				"#unique.seqs(fasta=" + outputDir + "/02_FASTA.fasta);\"";
		*/
	}

	// =========================================
	// Private feilds
	// =========================================
	private File BBtoolsDir;
	private File outputDir;
	private File mothurDir;

	private int quality16s = 35;
	private int quality18s = 160;
	private int length = 225;

}
