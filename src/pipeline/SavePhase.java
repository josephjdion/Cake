package pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SavePhase {
	private File outputDir;
	
	public SavePhase(Commands com) {
		this.outputDir = com.getOutputDir();
	}
	
	public void save(Pipeline.Phase phase) {
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
	
	public Pipeline.Phase load() {
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
		Pipeline.Phase phase = Pipeline.Phase.valueOf(strPhase);
		return phase;
	}

	
	
}
