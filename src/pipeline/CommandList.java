package pipeline;
/**
 * This is used to store the various sequence of commands for analysis
 * @author Joe
 *
 */
public class CommandList {
	
	/**
	 * Currently the only process used 
	 * @param com Uses existing commands class.
	 */
	public static void executeStandardAnalysis(Commands com)
	{
		sop("Starting Cake");
		
		// merge
		com.commandExecute(com.getMerge());
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
	
	private static void sop(String str){System.out.println(str);}
	
}
