package pipeline;

import java.io.File;
import java.util.ArrayList;

/**
 * This stores the fq pairs, and the information they have in common.
 * @author Joe
 *
 */
public class FastqPair {
	/** Stores the actual fastq files */
	private ArrayList<File> fqFiles = new ArrayList<File>();
	/** Stores the elements of the  */
	private String[] elements;
	private boolean is16S;
	private String baseName;
	
	public FastqPair(File fq1, File fq2) {
		fqFiles.add(fq1);
		fqFiles.add(fq2);
		populateElements();
		determineRNALength();
		determineBaseName();
	}
	public FastqPair(ArrayList<File> fqFiles) {
		this.fqFiles = fqFiles;
		populateElements();
		determineRNALength();
		determineBaseName();
	}
	
	/** Gets the proper length for this sequence */
	private void determineRNALength() {
		String RNALength = getRNALength();
		if(RNALength.equals("16S"))
			is16S = true;
		else
			is16S = false;
	}
	
	/** Populates the elements, this parses the file and puts it in the elemenets arraylist */
	private void populateElements() {
		String fq1 = this.fqFiles.get(0).getName();
		this.elements = fq1.split("_");	}
	
	private void determineBaseName() {
		this.baseName = elements[0];
	}
	
	
	// Getters
	public String getBaseName() {return this.baseName;}
	
	public ArrayList<File> getFiles(){return this.fqFiles;}
	
	public boolean is16S(){return is16S;}
	
	private String getRNALength() {
		return elements[1];
	}
	@Override
	public String toString() {
		return fqFiles.get(0).toString() + " " + fqFiles.get(1).toString();
	}
	
	
	
}
