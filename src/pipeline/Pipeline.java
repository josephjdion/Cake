package pipeline;

import java.io.*;
import java.util.*;
import java.util.stream.*;


/*
 Pipeline design goals:
 
 1) Simplify file names. Make it as easy as possible to read a dir listing and understand
 what's going on. Rename complicated file names, and delete unnecessary files.
 
 2) Seamless startup after a crash or failure. On startup, pipeline should inspect file names
 to figure out where the last run left off, and pick up from there.
 
 3) Parallelizable. Methods called in parallel streams can't throw any checked exceptions.
 
 4) Clean implementation. With luck we'll get funded to analyze lots more data using this
 same pipeline, but probably not until some time has passed and we've all forgotted what the
 code does. So plentiful comments and beautiful formatting. Also, the code might get distributed,
 so we have to be proud of it.
 */
/**
 * 
 * @author Joe
 *
 */
public class Pipeline
{
	private static EnvironmentInfo EI;
	private File		inputDirf;
	
	/**
	 * @param inputDirf The directory containing the fastq files you wish to work with
	 * @param ei Conains the information for your local machine
	 */
	Pipeline(File inputDirf, EnvironmentInfo ei) {
		this.inputDirf = inputDirf;
		this.EI = ei;
	}
	/**
	 * This is the method that puts all the methods needed for analysis in the correct
	 * sequence. It will create a list of all fastq pairs and then it will create a new 
	 * thread for each pair to execute the selected analysis.
	 */
	public void execute()
	{	
		ArrayList<File> fqFiles =  new ArrayList<File>();
		// popululate fqFiles
		Arrays.stream(inputDirf.list())
			.filter(fname -> fname.endsWith(".fastq"))
			.map(fname -> new File(inputDirf, fname))
			.forEach(fastq -> fqFiles.add(fastq));
		// take all the fqs and put them into matching pairs
		ArrayList<FastqPair> pairs = makePairs(fqFiles);
		pairs.stream()
			.forEach(fastqPair -> execute(fastqPair));
		// sort fqfileNames
	}
	
	/**
	 * Pairs files together in a given directory
	 * @param filesToPair the files you wish to pair
	 * @return a list of all files that need to be paired
	 */
	private ArrayList<FastqPair> makePairs(ArrayList<File> filesToPair) {
		ArrayList<FastqPair> returnArray = new ArrayList<FastqPair>();
		int size = filesToPair.size();
		// get file to check first
		for (int i = 0; i < size; i++) {
			ArrayList<File> initialPair = new ArrayList<File>();
			initialPair.add(filesToPair.get(i));
			String baseName = getBaseName(filesToPair.get(i));
			// then find a matching file in the remaining arraylist;
			for (int j = i+1; j < size; j++) {
				File fileToCheck = filesToPair.get(j);
				if(getBaseName(fileToCheck).equals(baseName))
				{
					initialPair.add(fileToCheck);
					returnArray.add(new FastqPair(initialPair));
					break;
				}
			}
		}
		return returnArray;
	}
	
	/**
	 * Gets the base name of fastqFile
	 * @param fastqFile The fastq file to extract the base name from
	 * @return The base name of the given fastq
	 */
	private String getBaseName(File fastqFile) {
		String fq1 = fastqFile.getName();
		String[] elements = fq1.split("_");
		return elements[0];
	}
	
	/**
	 * 
	 * @param pair the pair we 
	 */
	private void execute(FastqPair pair)
	{
		Commands com = new Commands(EI, pair);
		CommandList comList = new CommandList();
		comList.executeStandardAnalysis(com);
	}
}