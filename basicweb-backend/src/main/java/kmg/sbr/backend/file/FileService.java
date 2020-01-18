package kmg.sbr.backend.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public boolean deleteFile(String fileName) throws IOException;
	public FileInfo getPath(MultipartFile file) throws IOException;
	public void storeFile(MultipartFile file, FileInfo fileInfo) throws IOException;
	public void replaceFile(String originalPath, MultipartFile targetFile,FileInfo fileInfo) throws IOException;
	public String getBase64DataOfImage(String path) throws IOException;
}
