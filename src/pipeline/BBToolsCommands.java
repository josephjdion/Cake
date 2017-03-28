package pipeline;

import java.io.File;

/**
 * This contains the commands BBTools needs given the input files specified by
 * the command Object that is passed in.
 * 
 * @author Joe
 *
 */
public class BBToolsCommands {

	// =========================================
	// Fields
	// =========================================

	private Commands com;

	// =========================================
	// Constructors
	// =========================================

	public BBToolsCommands(Commands Com) {
		this.com = Com;
	}

	// =========================================
	// BBTool String getters
	// =========================================

	/**
	 * This gives a properly formatted merge command
	 * 
	 * @param in1
	 *            First file to be merged
	 * @param in2
	 *            Second file to be merged
	 * @return Merge command
	 */
	protected String getMergeStr(File in1, File in2) {
		// BBTools Directory, In1, In2, output directory
		return String.format(MergeStr, com.BBtoolsDir, in1, in2, com.outputDir);
	}

	/**
	 * Gives trim command
	 * 
	 * @param quality
	 *            The quality needed for the trimming
	 * @param minLength
	 *            The minimum acceptible length for the trimming
	 * @return Trim command
	 */
	protected String getTrimStr(int quality, int minLength) {
		// BBTools Directory, output folder, output folder, trim quality, length
		return String.format(TrimStr, com.BBtoolsDir, com.outputDir, quality, minLength);
	}

	// =========================================
	// Final Static Strings
	// =========================================

	// BBTools Directory, output folder, output folder, trim quality, length
	private static final String TrimStr = "%1$s/bbduk.sh in=%2$s/00_MERGED.fastq out=%2$s/01_TRIMMED.fastq "
			+ "qtrim=rl trimq=%3$s minlen=%4$s";

	// BBTools Directory, In1, In2, output directory
	private static final String MergeStr = "%s/bbmerge.sh in1=%s in2=%s out=%s/00_MERGED.fastq";

}
