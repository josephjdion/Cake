package pipeline;

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

		// output dir, output dir
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
	private static final String UniquifyStr = "set.dir(%1$s)\n" 
			+ "set.dir(output=%1$s)\n"
			+ "unique.seqs(fasta=02_FASTA.fasta)\n"
			+ "rename.file(names = silva.bacteria.v4";
}
