package ecommerce.repository;

import ecommerce.models.Variation;
import ecommerce.models.VariationOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {

    @Query(value="select varOp from VariationOption varOp where varOp.variation.id = :id")
    List<VariationOption> selectVariationOptionOfVariation(long id);

    /**
     * Select name of a variation of variation option
     * Usage: show configuration in the Product item component.
     * @param id Variation option id
     * @return Name of variation
     */
    @Query(value="select varOp.variation.name from VariationOption varOp where varOp.id = :id")
    String selectNameOfVariationByVariationOptionId(long id);

    @Query(value="select varOp.variation from VariationOption varOp where varOp.id = :id")
    Variation selectVariationByVariationOptionId(long id);
}
