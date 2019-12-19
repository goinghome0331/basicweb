package kmg.sbr.backend.file;

import java.nio.file.Path;

public class FileInfo {
	Path fullPath;
	String halfPath;
	
	public FileInfo(Path fullPath, String halfPath) {
		super();
		this.fullPath = fullPath;
		this.halfPath = halfPath;
	}
	public Path getFullPath() {
		return fullPath;
	}
	public void setFullPath(Path fullPath) {
		this.fullPath = fullPath;
	}
	public String getHalfPath() {
		return halfPath;
	}
	public void setHalfPath(String halfPath) {
		this.halfPath = halfPath;
	}
	
	
}
