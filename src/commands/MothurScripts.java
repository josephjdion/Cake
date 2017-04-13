package commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pipeline.Commands;

/**
 * As opposed to the terminal commands stored in BBToolsCommands, MothurScripts
 * contains scripts that are written to batch files (with the exception of run
 * and delete.) Input and environment variables are determined by the Command
 * object that is passed in.
 * @author Joe
 */
public class MothurScripts {
	
	private Commands com;
	private File MothurDir;
	private File OutputDir;
	private File ExecutionDir;
	private String baseName;
	private String numOfProcessors;
	private String silvaVersion;

	public MothurScripts(Commands Com) {
		
		this.OutputDir = Com.getOutputDir();
		this.MothurDir = Com.getMothurDir();
		this.ExecutionDir = new File (System.getProperty("user.dir"));
		this.baseName = Com.getBaseName();
		this.numOfProcessors = Com.getNumOfNumOfProcessors();
		this.silvaVersion = Com.getSilvaVer();
	}

	/**
	 * Run mothur's unique function with the previously specified fastq files
	 */
	public void uniquify() {
		String script =
		String.format(UniquifyBatchScript, OutputDir, MothurDir,  ExecutionDir, numOfProcessors, silvaVersion);
		createAndExecuteBatch(script);
	}
	
	/**
	 * Runs mothur's classify function with the previously specified fastq files
	 */
	public void classify() {
		String script =
		// output dir
		String.format(ClassifyBatchScript, OutputDir, MothurDir, ExecutionDir,
				 silvaVersion, numOfProcessors);
		createAndExecuteBatch(script);
	}
	
	/**
	 * Executes the batch file for mothur
	 * See https://www.mothur.org/wiki/Batch_mode for usage details
	 */
	private void executeBatch() {
		String command =
		// mohtur dir, mothur dir
		String.format(executeCommand, MothurDir, baseName);
		CommandExec.exec(command);
		
	}
	
	/**
	 * Deletes the batch file created
	 */
	private void deleteBatch() {
		String command = 
		// mothur dir
		String.format(deleteCommand, MothurDir, baseName);
		CommandExec.exec(command);
	}
	
	/**
	 * This method contains all of the methods used in the execution
	 * of a mothur batch file.
	 * @param scriptToWrite The script to write to the batch file
	 */
	private void createAndExecuteBatch(String scriptToWrite) {
		createBatchFile(scriptToWrite);
		 executeBatch();
		 deleteBatch();
	}

	/**
	 * Creates the batch
	 * @param scriptToWrite
	 */
	private void createBatchFile(String scriptToWrite) {
		File file = new File(MothurDir + "/MyBatch" + baseName);
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
	// =========================================
	// Mothur Scripts
	// =========================================
	
	// execution direction
	private final String deleteLogFile = "rm %s/*.logfile";
	
	// mothur dir
	private final String deleteCommand = "rm %s/MyBatch%2$s";
	// mohtur dir, mothur dir
	private final String executeCommand = "%1$s/mothur %1$s/MyBatch%2$s";
	
	// output dir, outputdir, execution dir, numOfProcessors
	private final String UniquifyBatchScript = 
			  "set.dir(%1$s)\n" + "set.dir(output=%1$s)\n"
			+ "unique.seqs(fasta=02_FASTA.fasta)\n" + "system(mv %1$s/02_FASTA.names %1$s/03_NAMES.txt, " + "processors=%3$s)\n"
			+ "system(mv %1$s/02_FASTA.unique.fasta %1$s/04_UNIQUE.fasta)\n"
			+ "system(" + String.format(deleteLogFile, ExecutionDir) + ")\n"
			+ "system(rm %2$s/*.logfile)\n" ;

	// output dir, mothurdir, executiondir, silva version, numOfProcessers
	private final String ClassifyBatchScript = 
			  "set.dir(%1$s)\n" + "classify.seqs(fasta=%1$s/04_UNIQUE.fasta, "
			+ "taxonomy=%2$s/silva.nr_v%4$s.tax, " + "reference=%2$s/silva.nr_v%4$s.align, " + "processors=%5$s)\n"
			+ "system(rm %1$s/04_UNIQUE.nr_v%4$s.wang.flip.accnos)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v%4$s.wang.tax.summary %1$s/05_TREE.txt)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v%4$s.wang.taxonomy %1$s/6_TAXONOMY.txt)\n"
			+ "system(rm %3$s/*.logfile)\n" ;

}
