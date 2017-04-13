package pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves environment info of the local machine. Has default values that need
 * to be overriden
 * 
 * @author Joe
 */

public class SaveEnvironment {
	// hyper threaded CPU Virtualized cores
	// Do not modify directly use the setters
	private  String numberOfVirtualizedProcessors = "numOfProcessors=8";
	private  String mothurDir = "mothurDir=/bbmap";
	private  String bbToolsDir = "bbToolsDir=/mothur";
	private  String silvaRefVersion = "silvaV=123";

	// Save variables
	// Remember to change the values from their default
	public void saveEnvironment() {
		File file = new File("CakeFile");
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(bbToolsDir +"\n");
			fw.write(mothurDir+"\n");
			fw.write(numberOfVirtualizedProcessors+"\n");
			fw.write(silvaRefVersion+"\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public EnvironmentInfo loadEnvironmentInfo() {
		EnvironmentInfo ei = new EnvironmentInfo();
		File file = new File("CakeFile");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			readText(br);
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// now set the fields of ei
		ei.setBBToolsDir(new File (parseLine(this.bbToolsDir)));
		ei.setMothurDir(new File (parseLine(this.mothurDir)));
		// get stored value as string
		String val = parseLine(this.numberOfVirtualizedProcessors);
		// convert to integer
		int numberOfProc = Integer.parseInt(val);
		ei.setNumOfProcessors(numberOfProc);
		// get stored value as string
		val = parseLine(this.silvaRefVersion);
		// convert to integer
		int version = Integer.parseInt(val);
		ei.setSilva(version);
		return ei;
	}

	/**
	 * This sets the private fields of this class to be packaged into an enviromentInfo class.
	 * @param br This specifiys what file to read
	 */
	private void readText(BufferedReader br) {
		try {
		String val = parseLine(br.readLine());
		setBBToolsDir(val);
		val = parseLine(br.readLine());
		setMothurDir(val);
		val = parseLine(br.readLine());
		setNumOfProcessors(Integer.parseInt(val));
		val = parseLine(br.readLine());
		setSilvaRefVersion(Integer.parseInt(val));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates the return array with elements from the input string
	 * @param strToParse String to be parsed
	 * @return
	 */
	private String parseLine(String strToParse) {
		String[] tokens = strToParse.split("=");
		return tokens[1];
	}

	// Set up enviornment before saving
	public  void setNumOfProcessors(int num) {
		numberOfVirtualizedProcessors = "numOfProcessors=" + num;
	}

	public  void setMothurDir(String path) {
		mothurDir = "mothurDir=" + path;
	}

	public  void setBBToolsDir(String path) {
		bbToolsDir = "bbToolsDir=" + path;
	}

	public  void setSilvaRefVersion(int version) {
		silvaRefVersion = "silvaV=" + version;
	}

	public void saveEnvironment(EnvironmentInfo infoToSave) {
		setBBToolsDir(infoToSave.getBBToolsDir().toString());
		setMothurDir(infoToSave.getMothurDir().toString());
		setSilvaRefVersion(infoToSave.getSilvaVersion());
		setNumOfProcessors(infoToSave.getNumOfProcessors());
		
		saveEnvironment();
	}

}
