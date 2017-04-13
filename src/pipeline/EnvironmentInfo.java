package pipeline;

import java.io.File;

/**
 * This is the object used to store various environment variables. It needs to
 * include more information in the future such as the version of silva. This
 * currently ONLY uses silva v123.
 * 
 * @author Joe @
 */
public class EnvironmentInfo {

	private  File BBToolsDir;
	private  File mothurDir;
	// This refers to what version of silva is on the machine
	private  int silvaRefVersion;
	// This integer refers to number of virtualized processors, see:
	// http://www.intel.com/content/www/us/en/architecture-and-technology/hyper-threading/hyper-threading-technology.html
	private  int numOfProcessors;

	public EnvironmentInfo() {
		// TODO Auto-generated constructor stub
	}

	public EnvironmentInfo(File bbToolsDir, File mothurDir) {
		this.BBToolsDir = bbToolsDir;
		this.mothurDir = mothurDir;
	}

	public File getBBToolsDir() {
		return BBToolsDir;
	}

	public File getMothurDir() {
		return mothurDir;
	}

	public int getNumOfProcessors() {
		return numOfProcessors;
	}
	
	public void setMothurDir(File dir) {
		this.mothurDir =  dir;
	}
	
	public void setMothurDir(String mothurPath) {
		this.mothurDir = new File(mothurPath);
	}
	
	public void setBBToolsDir(File dir) {
		this.BBToolsDir = dir;
	}
	
	public void setBBToolsDir(String bbPath) {
		this.BBToolsDir = new File(bbPath);
	}
	
	public void setNumOfProcessors(int num) {
		this.numOfProcessors = num;
	}
	
	public void setNumOfProcessors(String numString) {
		this.numOfProcessors = Integer.parseInt(numString);
	}
	
	public void setSilva(int version) {
		this.silvaRefVersion = version;
	}
	
	public void setSilva(String versionString) {
		this.silvaRefVersion = Integer.parseInt(versionString);
	}
	
	public int getSilvaVersion() {
		return silvaRefVersion;
	}
	
}
