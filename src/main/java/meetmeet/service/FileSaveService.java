package meetmeet.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSaveService {
	
	public void saveFile(MultipartFile file, String fileName, String defaultFileName) throws IOException{
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
		File saveFile = new File(projectPath, fileName);
		if (file.getSize() == 0) {
			File defaultFile = new File(projectPath + defaultFileName);
			Files.copy(defaultFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} else {
			file.transferTo(saveFile);
		}
	}
}
