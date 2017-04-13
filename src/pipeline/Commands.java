package pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import commands.BBToolsCommands;
import commands.InternalCommands;
import commands.MothurScripts;
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

	private String baseName;
	protected FastqPair fastqPair;
	
	private int quality = 35;
	private int length16s = 225;
	private int length18s = 160;

	// Instances to run further commands off of
	protected BBToolsCommands bbTools;
	protected MothurScripts mothur;
	protected InternalCommands internal;
	
	private String numOfProcessors;
	private String silvaRefVersion;

	/**
	 * 
	 * @param EI
	 *            This is used to determine the install directories of several
	 *            important programs
	 * @param FastqPair
	 *            The pair of fastqs to be used
	 */
	public Commands(EnvironmentInfo EI, FastqPair fastqPair) {
		this.BBtoolsDir = EI.getBBToolsDir();
		this.mothurDir = EI.getMothurDir();
		this.fastqPair = fastqPair;
		this.setOutputFolder(this.fastqPair);
		this.baseName = trimName(outputDir.getName());
		this.numOfProcessors = "" + EI.getNumOfProcessors();
		this.silvaRefVersion = "" + EI.getSilvaVersion();
		// Initiate these three last
		this.bbTools = new BBToolsCommands(this);
		this.mothur = new MothurScripts(this);
		this.internal = new InternalCommands(this);

	}
	
	/**
	 * Sets the output folder for this class 
	 * @param file the file
	 */
	private void setOutputFolder(FastqPair fastqPair) {
		File file = fastqPair.getFiles().get(0);
		this.outputDir = new File(file.getParentFile().getAbsolutePath() + "/" + this.trimName(file.getName()));
	}
	/**
	 * Given a base name of a certain file it will trim the excess 
	 * off of the file
	 * @param fileName
	 * @return
	 */
	private String trimName(String fileName) {
		String returnStr = "";
		for (int i = 0; i < fileName.length(); i++) {
			if (fileName.charAt(i) == '_')
				break;
			else
				returnStr += fileName.charAt(i);
		}
		return returnStr;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public void setLength8s(int length18s) {
		this.length18s = length18s;
	}

	public void setLength(int length16s) {
		this.length16s = length16s;
	}

	public File getOutputDir() {
		return this.outputDir;
	}

	public File getBBToolsDir() {
		return this.BBtoolsDir;
	}

	public File getMothurDir() {
		return this.mothurDir;
	}

	public FastqPair getFastqPair() {
		return this.fastqPair;
	}

	public int getQuality() {
		return this.quality;
	}

	public int get16Length() {
		return this.length16s;
	}

	public int get18sLength() {
		return this.length18s;
	}
	
	public String getBaseName() {
		return this.baseName;
	}
	
	public String getNumOfNumOfProcessors() {
		return this.numOfProcessors;
	}
	
	public String getSilvaVer() {
		return this.silvaRefVersion;
	}

}
