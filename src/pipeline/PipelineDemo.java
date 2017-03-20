package pipeline;

import java.io.File;

public class PipelineDemo {
public static void main(String[] args) {
	
	// these will be taken out and replaced with a stream of these files
	File in1 = new File("/Volumes/Work/BioProj/fastq/TestS1L14_16S_1.fastq");
	File in2 = new File("/Volumes/Work/BioProj/fastq/TestS1L14_16S_2.fastq");
	// output directory will be automatically generated based off of the name of the input files
	File out = new File("/Volumes/Work/BioProj/fastq");
	// these directories will be saved onto a local file so they will not need to be specified each time
	File bbToolDir = new File("/Users/Joe/BioApps/bbmap");
	File mothurDir = new File("this wont work");
	
	
	// in the future bbToolDir and mothurDir should be stored in the save file
	Commands com = new Commands(out, bbToolDir, mothurDir);
	
	
	sop(com.getMerge(in1, in2));
	sop(com.get16sTrim());
	com.makeFasta();
	
	
}


public static void sop(Object obj){System.out.println(obj);}
}
