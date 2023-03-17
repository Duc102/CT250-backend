package ecommerce.services;

import ecommerce.models.Variation;
import ecommerce.models.VariationOption;
import ecommerce.repository.VariationOptionRepository;
import ecommerce.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariationService {
    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    private VariationOptionRepository variationOptionRepository;

    public List<Variation> findVariationAlongCategory(Long id){
        return variationRepository.selectVariationAlongCategory(id);
    }

    public List<VariationOption> findVariationOptionOfVariation(Long id){
        return variationOptionRepository.selectVariationOptionOfVariation(id);
    }

    public String findNameOfVariationByVariationOptionId(long id){
        return variationOptionRepository.selectNameOfVariationByVariationOptionId(id);
    }

    public Variation findVariationByVariationOptionId(long variationOptionId){
        return variationOptionRepository.selectVariationByVariationOptionId(variationOptionId);
    }
}
