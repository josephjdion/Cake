package pipeline;

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

	// =========================================
	// Constructors
	// =========================================
	public MothurScripts(Commands Com) {
		this.com = Com;
	}
	// =========================================
	// Mothur Batch Strings
	// =========================================

	/**
	 * Returns uniqufy commands for batch script, via a batchfile !Do not use as
	 * terminal command!
	 * 
	 * @return uniquify script to input into batchfile
	 */
	protected String getUniquifyStr() {

		return String.format(UniquifyStr, com.outputDir);
	}

	/**
	 * Gives String for executing the batch file created earlier, using the base
	 * filename, via terminal
	 * 
	 * @return Batch file execution terminal command
	 */
	protected String getExecuteBatchStr() {
		// mohtur dir, mothur dir
		return String.format(executeStr, com.mothurDir);
	}

	/**
	 * Gives String for deletes the created batch file via terminal
	 * 
	 * @return
	 */
	protected String getDeleteStr() {
		// mothur dir
		return String.format(deleteStr, com.mothurDir);
	}

	protected String getClassify() {
		// output dir
		return String.format(Classify, com.outputDir, com.mothurDir);
	}

	// =========================================
	// Private Fields
	// =========================================
	private Commands com;
	// =========================================
	// Strings
	// =========================================

	// mothur dir
	private static final String deleteStr = "rm %s/MyBatch";

	// mohtur dir, mothur dir
	private static final String executeStr = "%1$s/mothur %1$s/MyBatch";

	// output dir, output dir
	private static final String UniquifyStr = "set.dir(%1$s)\n" + "set.dir(output=%1$s)\n"
			+ "unique.seqs(fasta=02_FASTA.fasta)\n" + "system(mv %1$s/02_FASTA.names %1$s/03_NAMES.txt)\n"
			+ "system(mv %1$s/02_FASTA.unique.fasta %1$s/04_UNIQUE.fasta)\n";

	// output dir, mothur dir
	private static final String Classify = "set.dir(%1$s)\n" + "classify.seqs(fasta=%1$s/04_UNIQUE.fasta, "
			+ "taxonomy=%2$s/silva.nr_v123.tax, " + "reference=%2$s/silva.nr_v123.align, " + "processors=8)\n"
			+ "system(rm %1$s/04_UNIQUE.nr_v123.wang.flip.accnos)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v123.wang.tax.summary %1$s/05_TREE.txt)\n"
			+ "system(mv %1$s/04_UNIQUE.nr_v123.wang.taxonomy %1$s/6_TAXONOMY.txt)\n";

}
