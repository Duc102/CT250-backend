package ecommerce.repository;

import ecommerce.models.ProductCategory;
import ecommerce.uploadFile.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductCategoryRepositoryTest {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    public void getCategoryById(){
        ProductCategory productCategory = productCategoryRepository.findById(3001L).get();
        System.out.println(productCategory.getCategoryName());
        System.out.println(productCategory.getParentCategory().getParentCategory().getCategoryName());
    }

    @Test
    public void selectCategoryZeroLevel(){
        List<ProductCategory> productCategories = productCategoryRepository.selectCategoryZeroLevel();
        System.out.println(productCategories.size());
    }
    @Test
    public void selectFamilyTreeByCategoryId(){
        List<Integer> familyTree = productCategoryRepository.selectFamilyTreeByCategoryId(3L);
        familyTree.stream().forEach(System.out::println);
    }

    @Test
    public void check(){
        FileUtils.getExtension("Hello.png");
    }
}