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
 * 
 * @author Joe
 *
 */
public class MothurScripts {
	
	private Commands com;
	private File MothurDir;
	private File OutputDir;
	private File ExecutionDir;
	private String baseName;

	public MothurScripts(Commands Com) {
		
		this.OutputDir = Com.getOutputDir();
		this.MothurDir = Com.getMothurDir();
		this.ExecutionDir = new File (System.getProperty("user.dir"));
		baseName = Com.getBaseName();
	}

	public void uniquify() {
		String script =
		String.format(UniquifyBatchScript, OutputDir, ExecutionDir);
		createAndExecuteBatch(script);
	}
	
	public void classify() {
		String script =
		// output dir
		String.format(ClassifyBatchScript, OutputDir, MothurDir, ExecutionDir);
		createAndExecuteBatch(script);
	}

	private void executeBatch() {
		String command =
		// mohtur dir, mothur dir
		String.format(executeCommand, MothurDir, baseName);
		CommandExec.exec(command);
		
	}

	private void deleteBatch() {
		String command = 
		// mothur dir
		String.format(deleteCommand, MothurDir, baseName);
		CommandExec.exec(command);
	}
	
	
	private void createAndExecuteBatch(String toWrite) {
		createBatchFile(toWrite);
		 executeBatch();
		 deleteBatch();
	}

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
	
	// output dir, outputdir, execution dir
	private final String UniquifyBatchScript = 
			  "set.dir(%1$s)\n" + "set.dir(output=%1$s)\n"
			+ "unique.seqs(fasta=02_FASTA.fasta)\n" + "system(mv %1$s/02_FASTA.names %1$s/03_NAMES.txt, " + "processors=8)\n"
			+ "system(mv %1$s/02_FASTA.unique.fasta %1$s/04_UNIQUE.fasta)\n"
			+ "system(" + String.format(deleteLogFile, ExecutionDir) + ")\n"
			+ "system(rm %2$s/*.logfile)\n" ;

	// output dir, mothur dir, executionDir
	private final String ClassifyBatchScript = 
			  "set.dir(%1$s)\n" + "classify.seqs(fasta=%1$s/04_UNIQUE.fasta, "
			+ "taxonomy=%2$s/silva.nr_v123.tax, " + "reference=%2$s/silva.nr_v123.align, " + "processors=8)\n"
			+ "system(rm %1$s/04_UNIQUE.nr_v123.wang.flip.accnos)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v123.wang.tax.summary %1$s/05_TREE.txt)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v123.wang.taxonomy %1$s/6_TAXONOMY.txt)\n"
			+ "system(rm %2$s/*.logfile)\n" ;

}
