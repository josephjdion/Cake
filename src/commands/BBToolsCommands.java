package commands;

import java.io.File;

import pipeline.Commands;

/**
 * This contains the commands BBTools needs given the input files specified by
 * the command Object that is passed in.
 * 
 * @author Joe
 *
 */
public class BBToolsCommands {
	private File BBToolsDir;
	private File OutputDir;

	public BBToolsCommands(Commands Com) {
		this.BBToolsDir = Com.getBBToolsDir();
		this.OutputDir = Com.getOutputDir();
	}

	protected String getMergeCommandStr(File fastq1, File fastq2) {
		// BBTools Directory, In1, In2, output directory
		return String.format(MergeStr, BBToolsDir, fastq1, fastq2, OutputDir);
	}

	protected String getTrimCommandStr(int quality, int minLength) {
		// BBTools Directory, output folder, output folder, trim quality, length
		return String.format(TrimStr, BBToolsDir, OutputDir, quality, minLength);
	}

	// =========================================
	// Final Static Strings (Scripts)
	// =========================================

	// BBTools Directory, output folder, output folder, trim quality, length
	private static final String TrimStr = "%1$s/bbduk.sh in=%2$s/00_MERGED.fastq out=%2$s/01_TRIMMED.fastq "
			+ "qtrim=rl trimq=%3$s minlen=%4$s";

	// BBTools Directory, In1, In2, output directory
	private static final String MergeStr = "%s/bbmerge.sh in1=%s in2=%s out=%s/00_MERGED.fastq";

}
