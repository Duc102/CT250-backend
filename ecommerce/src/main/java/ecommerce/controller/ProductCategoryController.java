package ecommerce.controller;

import ecommerce.dao.productCategory.ProductCategoryDao;
import ecommerce.models.ProductCategory;
import ecommerce.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/administrator/productCategory")
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/{id}")
    public ProductCategory getProductCategory(@PathVariable Long id){
        return productCategoryService.findCategoryById(id);
    }

    @GetMapping("/level/0")
    public List<ProductCategory> getAllProductCategoryZeroLevel(){
        return productCategoryService.findAllCategoryZeroLevel();
    }

    @GetMapping("/{id}/children")
    public List<ProductCategoryDao> getChildrenOfProductCategory(@PathVariable long id){
        return productCategoryService.findChildrenOfCategory(id);
    }

    @GetMapping("/search/{name}")
    public  List<ProductCategory> getProductCategoryByName(@PathVariable String name) {
        return productCategoryService.findProductCategoryByName(name); }
}
