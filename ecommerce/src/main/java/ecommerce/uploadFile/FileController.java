package ecommerce.uploadFile;

import ecommerce.Const.Disk;
import ecommerce.models.ProductImage;
import ecommerce.models.ProductItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileController {

    public static List<String> listAllFilesOfDirectory(String dir){
        File[] d = new File(dir).listFiles();
        System.out.println(d);
        if(d != null)
            return Stream.of(d)
                    .map(File::getName)
                    .collect(Collectors.toList());
        else return new ArrayList<>();
    }
    public static void deleteDirectory(String path){
        String fullLocation = Disk.source + path;

        List<String> files = listAllFilesOfDirectory(fullLocation);
//        Delete all files before delete directory
        files.forEach(file->{
            File f = new File(fullLocation+"/"+file);
            if(f.isDirectory()){
                deleteDirectory((fullLocation+"/"+file));
            } else
                f.delete();
        });
//        Delete directory
        File file = new File(fullLocation);
        file.delete();
    }
    public static void main(String[] args) throws IOException {
        String path = "/frontend/ecommerce/public/Images/Products/54";
        String fullLocation = Disk.source + path;
        List<String> files = listAllFilesOfDirectory(fullLocation);
        for (String file : files) {
            System.out.println(file);
        }
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
