package commands;

import java.io.File;

import pipeline.Commands;
import pipeline.FastqPair;

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
	private FastqPair fastqPair;
	private File fastq1;
	private File fastq2;
	private int quality;
	private int length16s;
	private int lenght18s;
	
	public BBToolsCommands(Commands Com) {
		this.BBToolsDir = Com.getBBToolsDir();
		this.OutputDir = Com.getOutputDir();
		this.fastqPair = Com.getFastqPair();
		this.fastq1 = fastqPair.getFiles().get(0);
		this.fastq2 = fastqPair.getFiles().get(1);
		this.length16s = Com.get16Length();
		this.lenght18s = Com.get18sLength();
		this.quality = Com.getQuality();
	}

	public void merge() {
		String command =
		// BBTools Directory, In1, In2, output directory
		String.format(MergeStr, BBToolsDir, fastq1, fastq2, OutputDir);
		CommandExec.exec(command);
	}
	
	public void trim() {
		if(fastqPair.is16S())
			Trim(length16s);
		else
			Trim(lenght18s);
	}

	private void Trim(int minLength) {
		String command =
		// BBTools Directory, output folder, output folder, trim quality, length
		String.format(TrimStr, BBToolsDir, OutputDir, quality, minLength);
		CommandExec.exec(command);
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
