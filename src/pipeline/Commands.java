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

	// =========================================
	// Constructors
	// =========================================

	/**
	 * 
	 * @param EI
	 *            This is used to determine the install directories of several
	 *            important programs
	 * @param FastqPair
	 *            The pair of fastqs to be used
	 */
	public Commands(EnviromentInfo EI, ArrayList<File> FastqPair) {

		this.setOutputFolder(FastqPair.get(0));

		this.BBtoolsDir = EI.getBBToolsDir();
		this.mothurDir = EI.getMothurDir();
		this.FastqFiles = FastqPair;

		this.BBToolCom = new BBToolsCommands(this);
		this.MothurScript = new MothurScripts(this);
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
		return BBToolCom.getMergeStr(fq1, fq2);
	}

	/**
	 * This gets the merge command using the fastq files in FastqFiles
	 * 
	 * @return returns the command for merge given the specified file name as a
	 *         String
	 */
	public String getMerge() {
		return BBToolCom.getMergeStr(this.FastqFiles.get(0), this.FastqFiles.get(1));
	}

	/**
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
	 * Given the specified quality by the method caller, return proper Trim
	 * command (16s or 18s)
	 * 
	 * @param qual
	 *            What quality to use 16s or 18s
	 * @return
	 */
	private String getTrim(int qual) {
		// BBTools Directory, output folder, output folder, trim quality, length
		return BBToolCom.getTrimStr(qual, this.length);
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
	 * This executes a string into the console.
	 * 
	 * @param command
	 */
	public void commandExecute(String command) {

		// System.out.println(command);

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

		createAndExecuteBatch(this.MothurScript.getUniquifyStr());
	}

	public void classify() {
		createAndExecuteBatch(this.MothurScript.getClassify());
	}
	// =========================================
	// Mothur's Helpers
	// =========================================

	/**
	 * This puts all the mothur helpers together
	 * 
	 * @param toWrite
	 */
	private void createAndExecuteBatch(String toWrite) {
		createBatchFile(toWrite);
		executeBatch();
		// deleteBatch();
	}

	/**
	 * This creates a batch file to do what we want
	 * 
	 * @param toWrite
	 *            What do we want to print in the
	 */
	private void createBatchFile(String toWrite) {

		File file = new File(mothurDir + "/MyBatch");
		FileWriter fw;

		try {
			file.createNewFile();
			fw = new FileWriter(file);

			fw.write(toWrite);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes generic batch file
	 */
	private void executeBatch() {
		commandExecute(MothurScript.getExecuteBatchStr());
	}

	/**
	 * Deletes the generic batch file
	 */
	private void deleteBatch() {
		commandExecute(MothurScript.getDeleteStr());
	}

	// =========================================
	// Fields
	// =========================================
	protected File BBtoolsDir;
	protected File outputDir;
	protected File mothurDir;

	protected int quality16s = 35;
	protected int quality18s = 160;
	protected int length = 225;

	private String baseName;

	private BBToolsCommands BBToolCom;
	private MothurScripts MothurScript;
	private ArrayList<File> FastqFiles;

	// =========================================
	// Boring Setters
	// =========================================
	public void setQuality16s(int quality16s) {
		this.quality16s = quality16s;
	}

	public void setQuality18s(int quality18s) {
		this.quality18s = quality18s;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
