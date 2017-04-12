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

public class Pipeline
{
	private static EnvironmentInfo EI;
	public enum Phase
	{
		MERGED, TRIMMED, FASTA, NAMES, UNIQUE, TREE, TAXONOMY, DONE;
		
		public String toString()
		{
			String s = "" + ordinal();
			if (ordinal() < 10) 
				s = '0' + s;
			s += "_"  + name() + getFilenameExtension();
			return s;
		}
		
		public String getName() {
			return this.name();
		}
		
		private String getFilenameExtension()
		{
			switch (this)
			{
				case MERGED:
				case TRIMMED:
					return ".fastq";
				case NAMES:
				case TAXONOMY:
				case TREE:
					return ".txt";
				default:
					return ".fasta";
			}
		}
	}
	
	
	private File		inputDirf;
	
	
	// 
	// The inputDirf contains paired-end fastq files. Pairs should have names that only
	// differ in their suffixes: xxx_1.fastq goes with xxx_2.fastq.
	//
	Pipeline(File inputDirf, EnvironmentInfo ei)
	{
		this.inputDirf = inputDirf;
		this.EI = ei;
	}
	
	
	void execute()
	{
		
		ArrayList<File> fqFiles =  new ArrayList<File>();
		
		// popululate fqFiles
		Arrays.stream(inputDirf.list())
			.filter(fname -> fname.endsWith(".fastq"))
			.map(fname -> new File(inputDirf, fname))
			.forEach(fastq -> fqFiles.add(fastq));
			//.forEach(fastq -> execute(fastq))
		
		// take all the fqs and put them into matching pairs
		ArrayList<FastqPair> pairs = makePairs(fqFiles);
		
		// for each pair execute the command on them
		pairs.stream()
			.forEach(fastqPair -> execute(fastqPair));
		
		// sort fqfileNames
		
	}
	
	/**
	 * This is a horribly ugly algorithm that pairs files together in a given directory
	 * @param filesToPair
	 * @return
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
	
	private String getBaseName(File f) {
		String fq1 = f.getName();
		String[] elements = fq1.split("_");
		return elements[0];
	}
	
	private void execute(FastqPair pair)
	{
		Commands com = new Commands(EI, pair);
		CommandList comList = new CommandList();
		comList.executeStandardAnalysis(com);
	}
	
	
	// This method can't throw any checked exceptions because it will be called in a stream.
	private void execute(File fastq)
	{
	
	}
	
	
	private static void sop(Object obj){System.out.println(obj);}
	
	// Tester fields
 

	
}
