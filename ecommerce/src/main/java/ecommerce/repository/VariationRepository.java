package ecommerce.repository;

import ecommerce.models.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariationRepository extends JpaRepository<Variation, Long> {

    @Query(value="select var from Variation var where var.productCategory.id = :id")
    List<Variation> selectVariationAlongCategory(Long id);


}

