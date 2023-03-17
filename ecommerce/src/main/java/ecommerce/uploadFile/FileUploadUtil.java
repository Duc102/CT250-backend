package ecommerce.uploadFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String fileName, String storageLocation, MultipartFile multipartFile) throws IOException {
        String directory = "D:/B1906657/NL/code/fullstack/frontend/ecommerce/public/Images" + storageLocation;
        File file = new File(directory);
        file.mkdir();
        Path uploadDirectory = Paths.get(directory);
        String extension = FileUtils.getExtension(multipartFile.getOriginalFilename());
        fileName=fileName.concat(".").concat(extension);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Error saving upload file: " + fileName, e);
        }
    }
}
