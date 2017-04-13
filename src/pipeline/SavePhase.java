package pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used for saving the phase of each execution process.
 * @author Joe
 *
 */
public class SavePhase {
	private File outputDir;
	
	public SavePhase(Commands com) {
		this.outputDir = com.getOutputDir();
	}
	/**
	 * Save the given phase in the specific output folder 
	 * @param phase Phase to save
	 */
	public void save(Phase phase) {
		File file = new File(outputDir + "/CakeSave.txt");
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(phase.getName());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the phase from the speciic output folder's save file
	 * @return the phase from the save file
	 */
	public Phase load() {
		String strPhase = "";
		File file = new File(outputDir + "/CakeSave.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			strPhase = br.readLine();
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();}
		Phase phase = Phase.valueOf(strPhase);
		return phase;
	}

	
	
}
