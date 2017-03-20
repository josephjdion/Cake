package pipeline;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
	// =========================================
	// Mothur Functions
	// =========================================

	
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
