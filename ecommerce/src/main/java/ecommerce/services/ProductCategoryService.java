package ecommerce.services;

import ecommerce.dao.productCategory.ProductCategoryDao;
import ecommerce.models.ProductCategory;
import ecommerce.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public ProductCategory findCategoryById(long id){
        return productCategoryRepository.findById(id).get();
    }

    public List<ProductCategory> findAllCategoryZeroLevel(){
        return productCategoryRepository.selectCategoryZeroLevel();
    }

    public List<ProductCategoryDao> findChildrenOfCategory(Long id){
        return productCategoryRepository.selectChildrenOfCategory(id);
    }
    public List<ProductCategory> findProductCategoryByName(String name) {
        return productCategoryRepository.findByCategoryNameContaining(name);
    }
}
