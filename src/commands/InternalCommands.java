package commands;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import pipeline.Commands;
import util.FastqReader;
/**
 * This handles commands that are executed inside of this program itself 
 * and not executed in the terminal.
 * @author Joe
 *
 */
public class InternalCommands {
	private File outputDir;
	public InternalCommands(Commands com) {
		outputDir = com.getOutputDir();
	}
	/**
	 * Converts the fastq File in the output folder to fasta
	 */
	public void makeFasta() {
		File outputFile = new File(outputDir.toString() + "/02_FASTA.fasta");
		// The input file is dependent on what BBToolsCommands names the file
		File inputFile = new File(outputDir.toString() + "/01_TRIMMED.fastq");
		try {
			FileReader fr = new FileReader(inputFile);
			FastqReader fqr = new FastqReader(fr);
			fqr.toFasta(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
