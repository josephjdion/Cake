package pipeline;

import java.io.File;

/**
 * This is the object used to store various environment variables. It needs to
 * include more information in the future such as the version of silva.
 * 
 * @author Joe @
 */
public class EnviromentInfo {

	// =========================================
	// Constructors
	// =========================================
	public EnviromentInfo() {
		// TODO Auto-generated constructor stub
	}

	public EnviromentInfo(File bbToolsDir, File mothurDir) {
		this.BBToolsDir = bbToolsDir;
		this.mothurDir = mothurDir;
	}

	// =========================================
	// Fields
	// =========================================
	private File BBToolsDir;
	private File mothurDir;

	// This refers to what version of silva is on the machine
	private File silvaRef;

	// This integer refers to number of virtualized processors (hyper threaded
	// cores)
	// See
	// http://www.intel.com/content/www/us/en/architecture-and-technology/hyper-threading/hyper-threading-technology.html
	private int numOfProcessors;

	// =========================================
	// Getters and Setters
	// =========================================

	public File getBBToolsDir() {
		return BBToolsDir;
	}

	private void setBBToolsDir(File bBToolsDir) {
		BBToolsDir = bBToolsDir;
	}

	public File getMothurDir() {
		return mothurDir;
	}

	private void setMothurDir(File mothurDir) {
		this.mothurDir = mothurDir;
	}

	public int getNumOfProcessors() {
		return numOfProcessors;
	}

	private void setNumOfProcessors(int numOfProcessors) {
		this.numOfProcessors = numOfProcessors;
	}

}
