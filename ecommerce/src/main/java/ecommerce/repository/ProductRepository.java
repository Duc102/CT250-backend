package ecommerce.repository;

import ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value="select pr from Product pr where pr.productCategory.id = :id")
    List<Product> selectProductByCategoryId(long id);

    @Query(value="select distinct pr from Product pr join fetch pr.productItems where pr.productCategory.id = :id")
    List<Product> selectProductsFetchItemByCategoryId(long id);

    List<Product> findByNameContaining(String name);

}
