package ecommerce.uploadFile;

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

    public static void saveDescription(String content){
        File description = new File("D:/Images/description.text");
        try {
            if(description.createNewFile()) {
                System.out.println("File created: " + description.getName());
            }
        } catch (Exception e){

        }
        try {

            FileWriter writer = new FileWriter("D:/Images/description.text");
            writer.write(content);

        } catch(Exception ex){
        }
    }
    public static void main(String[] args) throws IOException {
        File file = new File("D:/duc");
        file.createNewFile();
    }
}
