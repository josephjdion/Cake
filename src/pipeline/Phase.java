package pipeline;

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