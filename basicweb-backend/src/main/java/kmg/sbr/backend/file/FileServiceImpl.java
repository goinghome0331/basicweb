package kmg.sbr.backend.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kmg.sbr.backend.util.UserUtil;

@Service
public class FileServiceImpl implements FileService{
	
	
	private static Logger logger = Logger.getLogger(FileServiceImpl.class);
	private final Path uploadPath;
	private final Path homePath;
	@Value("${file.home-dir}")
	private String root;
	
	@Autowired
	public FileServiceImpl(FileStorageProperties fp) {
		this.uploadPath = Paths.get(fp.getUploadDir()).toAbsolutePath().normalize();
		this.homePath = Paths.get(fp.getHomeDir()).toAbsolutePath().normalize();
		createDirectories(this.uploadPath);
		createDirectories(this.homePath);
	}

	

	
	public String getBase64DataOfImage(String path) throws IOException{
		String fullPath = root + path;
		if(path == null) return null;
		FileInputStream in = new FileInputStream(fullPath);
		String base64 = new String(Base64.encodeBase64(IOUtils.toByteArray(in)));
		in.close();
		return base64;
	}
	
	private void createDirectories(Path path) {
		try {
			if(Files.exists(path)) {
				if(!Files.isDirectory(path)) 
					Files.delete(path);
				else
					return ;
			}
			Files.createDirectories(path);
		}catch(Exception e) {
			e.printStackTrace();
//			throw new FileStorageException("Could not create the directory where the uploaded files will be stored",e);
			// FileStorageException
		
		}
	}
	
	
	public boolean isExist(String fileName) {
		Path targetPath = homePath.resolve(fileName);
		return Files.exists(targetPath);
	}
	
	
	public boolean deleteFile(String fileName) throws Exception{

		if(fileName == null) {
			return false;
		}
		

		if(!isExist(fileName)) {
			return false;
		}
		

		Path targetPath = homePath.resolve(fileName);
		if(Files.isDirectory(targetPath)) { // 해당 파일이 디렉토리인 경우
			File dir = new File(targetPath.toUri());
			File[] files = dir.listFiles();
			for(File file : files) {
				Files.deleteIfExists(targetPath.resolve(file.getName()));
			}
		}

		Files.deleteIfExists(targetPath);
		logger.infof("Delete file = path : {}", fileName);
		
		return true;
	}
	
	public FileInfo getPath(MultipartFile file) throws Exception{
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String ret = null;
		
		if(UserUtil.getAuthenticatedUser() == null) {
			throw new Exception("Authenticated user is needed to save this file" + fileName);
		}
		if(fileName.contains("..")) {
			throw new Exception("Filename contains invalid path sequence " + fileName);
		}
			
		Path fullPath = null;


		String username = UserUtil.getAuthenticatedUser().getUsername();
		Path userPath = uploadPath.resolve(username);
		if(!Files.exists(userPath))	
			createDirectories(userPath);
				
		fullPath = userPath.resolve(fileName);
		String pathStr = fullPath.toUri().toURL().toString();
		String halfPath = pathStr.substring(pathStr.indexOf("uploads"));
		
		return new FileInfo(fullPath,halfPath);
	}
	public void storeFile(MultipartFile file, FileInfo fileInfo) throws Exception{
		Files.copy(file.getInputStream(),fileInfo.getFullPath(),StandardCopyOption.REPLACE_EXISTING);
		logger.infof("user file upload compeleted = path : {}", fileInfo.getHalfPath());
	}
	public void replaceFile(String originalPath, MultipartFile targetFile,FileInfo fileInfo) throws Exception{
		deleteFile(originalPath);
		storeFile(targetFile,fileInfo);
	}
}
