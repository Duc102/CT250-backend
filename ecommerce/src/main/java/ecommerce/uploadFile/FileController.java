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
    public static void main(String[] args) throws IOException {
        String root = System.getProperty("user.dir");
        String split[] = root.split("\\\\");
        String result = "";
        for(int i = 0; i< split.length - 2; i++)
            result=result.concat(split[i]+"/");
        System.out.println(result);
    }
}
