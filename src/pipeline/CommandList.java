package pipeline;
/**
 * This is used to store the various sequences of commands for analysis
 * @author Joe
 *
 */
public class CommandList {
	
	/**
	 * Currently the only process used
	 * @param com 
	 */
	public static void executeStandardAnalysis(Commands com)
	{
		sop("Starting Cake");
		
		// merge
		com.bbTools.merge();
		sop("Finished Merging");
		
		// trim
		com.bbTools.trim();
		sop("Finished Trimming");
		
		// make fasta
		com.internal.makeFasta();
		sop("Finished Fasta conversion");
		
		// uniquify
		com.mothur.uniquify();
		sop("Finished uniquify");
		
		// clasify
		com.mothur.classify();
		sop("Finished classify");
		
		sop("Finished All Test");
	}
	
	private static void sop(String str){System.out.println(str);}
	
}
