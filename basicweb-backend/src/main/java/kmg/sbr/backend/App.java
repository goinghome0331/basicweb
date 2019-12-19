package kmg.sbr.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;

import kmg.sbr.backend.config.SecurityConfig;
import kmg.sbr.backend.file.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
}
)
public class App {
	public static void main(String args[]) {
		SpringApplication.run(App.class, args);
	}
	
	
}
