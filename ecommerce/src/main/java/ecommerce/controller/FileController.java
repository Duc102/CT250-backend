package ecommerce.controller;

import ecommerce.uploadFile.FileUploadResponse;
import ecommerce.uploadFile.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("editName") String editName, @RequestParam String storageLocation) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        String url = FileUtils.saveFile(editName, storageLocation, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFilename(fileName);
        response.setSize(size);
        response.setDownloadUri(url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/uploadFile/json")
    public ResponseEntity<String> uploadJson(@RequestParam("fileName") String fileName, @RequestParam("storageLocation") String storageLocation, @RequestBody String file) throws IOException {
        FileUtils.saveJsonFile(fileName, storageLocation, file);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/readFile/productDescriptions/{id}")
    public String readProductDescriptionFile(@PathVariable long id) throws IOException {
        String path = "/Products/Descriptions/"+id+"/description.json";
        return FileUtils.readFile(path);
    }
}
