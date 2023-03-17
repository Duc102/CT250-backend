package ecommerce.repository;

import ecommerce.models.Variation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VariationRepositoryTest {

    @Autowired
    private VariationRepository variationRepository;

    @Test
    public void getAllVariation(){
        List<Variation> variationList = variationRepository.findAll();
        variationList.stream().forEach(var -> System.out.println(var.getProductCategory().getCategoryName()));
    }
}