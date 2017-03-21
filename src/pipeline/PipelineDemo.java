package pipeline;

import java.io.File;
import java.util.ArrayList;

public class PipelineDemo {
public static void main(String[] args) {
	
	// these will be taken out and replaced with a stream of these files
	File in1 = new File("/Volumes/Work/BioProj/fastq/TestS1L14_16S_1.fastq");
	File in2 = new File("/Volumes/Work/BioProj/fastq/TestS1L14_16S_2.fastq");
	// output directory will be automatically generated based off of the name of the input files
	File out = new File("/Volumes/Work/BioProj/fastq");
	// these directories will be saved onto a local file so they will not need to be specified each time
	File bbToolDir = new File("/Users/Joe/BioApps/bbmap");
	File mothurDir = new File("/Users/Joe/BioApps/mothur");
	
	
	// in the future bbToolDir and mothurDir should be stored in the save file
	Commands com = new Commands(out, bbToolDir, mothurDir);
	
	sop("Starting Cake");
	// merge
	com.commandExecute(com.getMerge(in1, in2));
	sop("Finished Merging");
	
	// trim
	com.commandExecute(com.get16sTrim());
	sop("Finished Trimming");
	
	// make fasta
	com.makeFasta();
	sop("Finished Fasta conversion");
	
	// uniquify
	com.commandExecute((com.uniquify()));
	sop(com.uniquify());
	sop("Finished uniquify");
	
	sop("Finished All Test");
	
}


public static void sop(Object obj){System.out.println(obj);}
}
