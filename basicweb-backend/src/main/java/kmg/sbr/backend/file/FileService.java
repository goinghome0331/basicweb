package kmg.sbr.backend.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public boolean deleteFile(String fileName) throws Exception;
	public FileInfo getPath(MultipartFile file) throws Exception;
	public void storeFile(MultipartFile file, FileInfo fileInfo) throws Exception;
	public void replaceFile(String originalPath, MultipartFile targetFile,FileInfo fileInfo) throws Exception;
	public String getBase64DataOfImage(String path) throws IOException;
}
