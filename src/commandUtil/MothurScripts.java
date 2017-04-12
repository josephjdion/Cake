package pipeline;

import java.io.File;

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

	public MothurScripts(Commands Com) {
		
		this.OutputDir = Com.getOutputDir();
		this.MothurDir = Com.getMothurDir();
	}

	protected String getUniquifyBatchScript() {
		return String.format(UniquifyBatchScript, OutputDir);
	}

	protected String getExecuteBatchCommand() {
		// mohtur dir, mothur dir
		return String.format(executeCommand, MothurDir);
	}

	protected String getDeleteCommand() {
		// mothur dir
		return String.format(deleteCommand, MothurDir);
	}

	protected String getClassifyBatchScript() {
		// output dir
		return String.format(ClassifyBatchScript, OutputDir, MothurDir);
	}
	// =========================================
	// Mothur Scripts
	// =========================================
	
	// mothur dir
	private static final String deleteCommand = "rm %s/MyBatch";
	// mohtur dir, mothur dir
	private static final String executeCommand = "%1$s/mothur %1$s/MyBatch";
	
	// output dir, output dir
	private static final String UniquifyBatchScript = 
			  "set.dir(%1$s)\n" + "set.dir(output=%1$s)\n"
			+ "unique.seqs(fasta=02_FASTA.fasta)\n" + "system(mv %1$s/02_FASTA.names %1$s/03_NAMES.txt)\n"
			+ "system(mv %1$s/02_FASTA.unique.fasta %1$s/04_UNIQUE.fasta)\n";

	// output dir, mothur dir
	private static final String ClassifyBatchScript = 
			  "set.dir(%1$s)\n" + "classify.seqs(fasta=%1$s/04_UNIQUE.fasta, "
			+ "taxonomy=%2$s/silva.nr_v123.tax, " + "reference=%2$s/silva.nr_v123.align, " + "processors=8)\n"
			+ "system(rm %1$s/04_UNIQUE.nr_v123.wang.flip.accnos)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v123.wang.tax.summary %1$s/05_TREE.txt)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v123.wang.taxonomy %1$s/6_TAXONOMY.txt)\n";

}
