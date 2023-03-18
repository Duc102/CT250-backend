package ecommerce.uploadFile;

import ecommerce.Const.Disk;
import ecommerce.models.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static String getExtension(String filename){
        String[] f = filename.split("\\.");
        String ext = f[f.length-1];
        return ext;
    }

    public static Set<String> listFilesOfDirectory(String dir){
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
    public static List<String> listAllFilesOfDirectory(String dir){
        File[] d = new File(dir).listFiles();
        if(d != null)
            return Stream.of(d)
                .map(File::getName)
                .collect(Collectors.toList());
        else return new ArrayList<>();
    }

    public static String saveFile(String fileName, String storageLocation, MultipartFile multipartFile) throws IOException {
        String directory = "D:/B1906657/NL/code/fullstack/frontend/ecommerce/public/Images" + storageLocation;
        File file = new File(directory);
        file.mkdir();
        Path uploadDirectory = Paths.get(directory);
        String extension = FileUtils.getExtension(multipartFile.getOriginalFilename());
        fileName = fileName.concat(".").concat(extension);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(fileName); // result: /link.png
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Error saving upload file: " + fileName, e);
        }
        return "/Images" + storageLocation + "/" + fileName;
    }

    public static void saveJsonFile(String fileName, String storageLocation, String json) throws IOException {
        String fullLocation = "D:/B1906657/NL/code/fullstack/Store" + storageLocation;
        File file = new File(fullLocation + "/" + fileName);
        file.createNewFile();
        FileWriter writer = new FileWriter(fullLocation + "/" + fileName);
        writer.write(json);
        writer.close();
    }


    public static String readFile(String storageLocation) throws IOException {
        String fullLocation = "D:/B1906657/NL/code/fullstack/Store" + storageLocation;
        Path filePath = Paths.get(fullLocation);
        String read = Files.readString(filePath);
        return read;

    }

    public static void deleteProductItemImagesFileIfNotNeed(List<ProductImage> fileNames) {
        String storage = "D:/B1906657/NL/code/fullstack/frontend/ecommerce/public";
        String productItemDir = fileNames.get(0).getUrl().substring(0, fileNames.get(0).getUrl().lastIndexOf('/'));
        String fullLocation = storage.concat(productItemDir);
        Set<String> filesExist = listFilesOfDirectory(fullLocation);
        List<String> need = fileNames.stream().map(fileName -> fileName.getUrl().substring(fileName.getUrl().lastIndexOf('/')+1)).collect(Collectors.toList());
        filesExist.forEach(file ->{
            if(!need.contains(file)){
                File delFile = new File(fullLocation+"/"+file);
                if(delFile.delete())
                    System.out.println("Delete successful!");
            }
        });
    }

    public static void copyAllFileInDirectory(String source, String destination){
        Set<String> files = listFilesOfDirectory(source);
        files.forEach(file-> {
            File f = new File(source+"/"+file);
            try(InputStream input = new FileInputStream(f)){
                Path path = Paths.get(destination, file);
                Files.copy(input,path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e){
                System.out.println(e);
            }
            f.delete();
        });
    }

    public static void deleteDirectory(String path){
        String fullLocation = Disk.source + path;

        List<String> files = listAllFilesOfDirectory(fullLocation);
//        Delete all files before delete directory
             files.forEach(file->{
                File f = new File(fullLocation+"/"+file);
                if(f.isDirectory()){
                    deleteDirectory((path+"/"+file));
                } else
                    f.delete();
            });
//        Delete directory
        File file = new File(fullLocation);
        file.delete();
    }
}
