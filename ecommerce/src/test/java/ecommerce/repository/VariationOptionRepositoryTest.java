package ecommerce.repository;

import ecommerce.models.VariationOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VariationOptionRepositoryTest {
    @Autowired
    private VariationOptionRepository variationOptionRepository;

    @Test
    public void getAllVariationOption(){
        List<VariationOption> variationOptionList = variationOptionRepository.findAll();
        variationOptionList.stream().forEach(varOp-> System.out.println(varOp.getValue()));
    }

    @Test
    public void get(){
        List<VariationOption> variationOptionList = variationOptionRepository.selectVariationOptionOfVariation(5L);
        System.out.println(variationOptionList.size());
    }
}