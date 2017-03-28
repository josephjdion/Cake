package pipeline;

import java.io.File;
import java.util.ArrayList;

public class PipelineDemo {
	/*
public static void main(String[] args) {
	
	// these will be taken out and replaced with a stream of these files
	File in1 = new File("/Volumes/Work/BioProj/fastq/TestS1L14_16S_1.fastq");
	File in2 = new File("/Volumes/Work/BioProj/fastq/TestS1L14_16S_2.fastq");
	// these directories will be saved onto a local file so they will not need to be specified each time
	File bbToolDir = new File("/Users/Joe/BioApps/bbmap");
	File mothurDir = new File("/Users/Joe/BioApps/mothur");
	
	
	
	
	EnviromentInfo EI = new EnviromentInfo(bbToolDir, mothurDir);
	
	ArrayList<File> Files = new ArrayList<File>();
	Files.add(in1);
	Files.add(in2);
	
	
	// in the future bbToolDir and mothurDir should be stored in the save file
	Commands com = new Commands(EI, Files);
	
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
	com.uniquify();
	sop("Finished uniquify");
	
	// clasify
	com.classify();
	sop("Finished classify");
	
	sop("Finished All Test");
	
}
*/

public static void sop(Object obj){System.out.println(obj);}
}
