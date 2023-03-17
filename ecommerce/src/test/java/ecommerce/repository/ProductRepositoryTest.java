package ecommerce.repository;

import ecommerce.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void getAllProduct(){
        List<Product> products = productRepository.findAll();
        products.stream().forEach(product -> {
            System.out.println(product.getName());
            System.out.println(product.getProductCategory().getCategoryName());
        });
    }
}