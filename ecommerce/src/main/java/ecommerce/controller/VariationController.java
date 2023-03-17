package ecommerce.controller;

import ecommerce.models.Variation;
import ecommerce.models.VariationOption;
import ecommerce.services.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("administrator/variation")
public class VariationController {
    @Autowired
    private VariationService variationService;

    @GetMapping("/nameOfVariation/variationOption/{id}")
    public String getNameOfVariationByVariationOptionId(@PathVariable long id){
        return variationService.findNameOfVariationByVariationOptionId(id);
    }

    /**
     * Get variations belong to a category
     * @param id Category id
     * @return List of variations
     */

    @GetMapping("/category/{id}")
    public List<Variation> getVariationAlongCategory(@PathVariable long id){
        return variationService.findVariationAlongCategory(id);
    }

    /**
     * Get variation options of a variation
     * @param id Variation id
     * @return List of variation options
     */
    @GetMapping("/{id}/variationOption")
    public List<VariationOption> getVariationOptionOfVariation(@PathVariable long id){
        return variationService.findVariationOptionOfVariation(id);
    }

    @GetMapping("/variationOption/{id}")
    public Variation getVariationByVariationOptionId(@PathVariable Long id){
        return variationService.findVariationByVariationOptionId(id);
    }
}
