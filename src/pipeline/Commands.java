package pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import util.FastqReader;

/**
 * Commands is the connecting class for environment info, input, scripts to be
 * used, and bash commands to be used. It takes the commands stored in
 * MothurScripts and BBToolsCommands and utilizes them here.
 * 
 * @author Joe
 *
 */
public class Commands {
	private File BBtoolsDir;
	private File outputDir;
	private File mothurDir;

	private int quality = 35;
	private int length16s = 225;
	private int length18s = 160;

	private String baseName;

	private BBToolsCommands BBToolCom;
	private MothurScripts MothurScript;

	private FastqPair fastqPair;
	/**
	 * 
	 * @param EI
	 *            This is used to determine the install directories of several
	 *            important programs
	 * @param FastqPair
	 *            The pair of fastqs to be used
	 */
	public Commands(EnviromentInfo EI, FastqPair fastqPair) {


		this.BBtoolsDir = EI.getBBToolsDir();
		this.mothurDir = EI.getMothurDir();
		this.fastqPair = fastqPair;
		this.setOutputFolder(this.fastqPair.getFiles().get(0));
		this.BBToolCom = new BBToolsCommands(this);
		this.MothurScript = new MothurScripts(this);
	}

	// =========================================
	// BBTools commands
	// =========================================
	public String getMergeCommand() {
		File fastq1 = this.fastqPair.getFiles().get(0);
		File fastq2 = this.fastqPair.getFiles().get(1);
		return BBToolCom.getMergeCommandStr(fastq1, fastq2);
	}
	
	public void getTrim() {
		if (fastqPair.is16S())
			executeCommand(get16sTrim());
		else
			executeCommand(get18sTrim());
	}

	/**
	 * @return returns a 16s trim of a file command as a string
	 */
	private String get16sTrim() {
		return getTrimCommand(length16s);
	}

	/**
	 * @return returns a 18s trim of a file command as a string
	 */
	private String get18sTrim() {
		return getTrimCommand(length18s);
	}

	/**
	 * Given the specified quality by the method caller, return proper Trim
	 * command (16s or 18s)
	 * @return
	 */
	private String getTrimCommand(int length) {
		// BBTools Directory, output folder, output folder, trim quality, length
		return BBToolCom.getTrimCommandStr(quality, length);
	}
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
	 * This executes a bash command into the console.
	 * @param command
	 */
	public void executeCommand(String command) {

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

	private void setOutputFolder(File file) {
		this.outputDir = new File(file.getParentFile().getAbsolutePath() + "/" + this.trimName(file.getName()));
	}

	private String trimName(String str) {
		String returnStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '_')
				break;
			else
				returnStr += str.charAt(i);
		}
		return returnStr;
	}

	// =========================================
	// Mothur Functions
	// =========================================
	public void uniquify() {

		createAndExecuteBatch(this.MothurScript.getUniquifyBatchScript());
	}

	public void classify() {
		createAndExecuteBatch(this.MothurScript.getClassifyBatchScript());
	}
	
	private void createAndExecuteBatch(String toWrite) {
		createBatchFile(toWrite);
		executeBatch();
		 deleteBatch();
	}

	private void createBatchFile(String scriptToWrite) {
		File file = new File(mothurDir + "/MyBatch");
		FileWriter fw;

		try {
			file.createNewFile();
			fw = new FileWriter(file);

			fw.write(scriptToWrite);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeBatch() {
		executeCommand(MothurScript.getExecuteBatchCommand());
	}

	private void deleteBatch() {
		executeCommand(MothurScript.getDeleteCommand());
	}
	
	public void setQuality16s(int quality16s) {
		this.quality16s = quality16s;
	}

	public void setQuality18s(int quality18s) {
		this.quality18s = quality18s;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public File getOutputDir()
	{
		return this.outputDir;
	}

	public File getBBToolsDir()
	{
		return this.BBtoolsDir;
	}
	
	public File getMothurDir()
	{
		return this.mothurDir;
	}
}
