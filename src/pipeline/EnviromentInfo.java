package pipeline;

import java.io.File;

/**
 * This is the object used to store various environment variables. It needs to
 * include more information in the future such as the version of silva. This
 * currently ONLY uses silva v123.
 * 
 * @author Joe @
 */
public class EnviromentInfo {

	private File BBToolsDir;
	private File mothurDir;
	// This refers to what version of silva is on the machine
	private File silvaRef;
	// This integer refers to number of virtualized processors, see:
	// http://www.intel.com/content/www/us/en/architecture-and-technology/hyper-threading/hyper-threading-technology.html
	private int numOfProcessors;

	public EnviromentInfo() {
		// TODO Auto-generated constructor stub
	}

	public EnviromentInfo(File bbToolsDir, File mothurDir) {
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
}
