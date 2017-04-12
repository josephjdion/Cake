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
	private enum Phase
	{
		MERGED, TRIMMED, FASTA, NAMES, UNIQUE, TREE, TAXONOMY;
		
		public String toString()
		{
			String s = "" + ordinal();
			if (ordinal() < 10) 
				s = '0' + s;
			s += "_"  + name() + getFilenameExtension();
			return s;
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
		CommandList.executeStandardAnalysis(com);
	}
	
	
	// This method can't throw any checked exceptions because it will be called in a stream.
	private void execute(File fastq)
	{
		
		
		
		
		
		/*
		 * 1st several steps use bbtools. See http://jgi.doe.gov/data-and-tools/bbtools/
		 * for download info. Later steps use mothur. See https://mothur.org.
		 
		 MERGED
		 Merge each pair: For xxx_1.fastq and xxx_2.fastq, make a subdir in FastqDirs called xxx. Put
		 merged output in FastqDirs/xxx/00_MERGED.fastq.
		 
bbmerge.sh in1=TestS1L14_16S_1.fastq in2=TestS1L14_16S_2.fastq out=FastqDirs/TestS1L14_16S/00_MERGED.fastq
		  
		  
		 TRIMMED
		 Trim to quality threshold = 35 and length = 225 for 16S, 160 for 18S. 
bbduk.sh in=00_MERGED.fastq out=01_TRIMMED.fastq qtrim=rl trimq=35 minlen=225
		Creates 01_TRIMMED.fastq.
		 
		 
		 FASTA
		 Convert fastq to fastq. Use toFasta() method of palmer.util.FastqReader.
		 Creates 02_FASTA.fasta.
		 
		 
		 UNIQUE		 
		 Uniquify. Internal mothur command is 
unique.seqs(fasta=02_FASTA.fasta)  
		This creates 02_FASTA.names (which should be renamed 03_NAMES.txt), 
		02_FASTA.unique.fasta (which should be renamed 04_UNIQUE.fasta), 
		and a logfile called mothur.*.logfile (which should be deleted - all mothur
		logs s/b deleted right after creation).
		
		TAXONOMY and TREE
		Go to https://www.mothur.org/wiki/Silva_reference_files and click the "Full length 
		sequences and taxonomy references" link neart the top of that page to download 
		silva.nr_v123.align and silva.nr_v123.tax. Put them in the main mothur directory.
		
classify.seqs(fasta=04_UNIQUE.fasta,taxonomy=silva.nr_v123.tax, reference=silva.nr_v123.align)
		Starts up, says "Generating search database..." done in 6 mins, used ~20G of disk. 
		Then a few more minutes of misc processing. Builds some big files in mother dir, so
		hopefully it's only slow the first time. Creates 04_UNIQUE.nr_v123.wang.flip.accnos
		(probably s/b deleted), 04_UNIQUE.nr_v123.wang.tax.summary (s/b renamed 05_TREE.txt), 
		and 04_UNIQUE.nr_v123.wang.taxonomy (rename 06_TAXONOMY.txt). Don't worry about messages that
		say "Unable to open ..." if mothur keeps running.
		
		 */
	}
	
	
	private static void sop(Object obj){System.out.println(obj);}
	
	// Tester fields
 

	
}
