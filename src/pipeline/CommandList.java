package pipeline;
/**
 * This is used to store the various sequences of commands for analysis
 * @author Joe
 *
 */
public class CommandList {
	Pipeline.Phase phase;
	SavePhase save;
	/**
	 * Currently the only process used
	 * @param com 
	 */
	public void executeStandardAnalysis(Commands com)
	{
		
		String fileName = com.getBaseName() + " ";
		
		save = new SavePhase(com);
		phase = save.load();
		if(phase==Pipeline.Phase.DONE) return;
		sop(fileName + "Starting Cake");
		
		
		// merge
		if(phase==null) {
		com.bbTools.merge();
		setState(Pipeline.Phase.MERGED);
		}
		sop(fileName + "Finished Merging");
		
		// trim
		if(phase == Pipeline.Phase.MERGED) {
		com.bbTools.trim();
		setState(Pipeline.Phase.TRIMMED);
		}
		sop(fileName + "Finished Trimming");
		
		// make fasta
		if(phase == Pipeline.Phase.TRIMMED) {
		com.internal.makeFasta();
		setState(Pipeline.Phase.FASTA);
		}
		sop(fileName + "Finished Fasta conversion");
		
		// uniquify
		if(phase == Pipeline.Phase.FASTA) {
		com.mothur.uniquify();
		setState(Pipeline.Phase.UNIQUE);
		}
		sop(fileName + "Finished uniquify");
		
		// clasify
		if(phase == Pipeline.Phase.UNIQUE) {
		com.mothur.classify();
		setState(Pipeline.Phase.DONE);
		}
		
		sop(fileName + "Finished classify");
		
		sop(fileName + "Finished All Test");
	}
	private void setState(Pipeline.Phase currentPhase) {
		this.phase = currentPhase;
		save.save(phase);
	}
	private static void sop(String str){System.out.println(str);}
	
}
