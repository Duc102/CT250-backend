package ecommerce.repository;

import ecommerce.models.Product;
import ecommerce.models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    /**
     * Select all product items
     * @return List of product items
     */
    @Query(value = "select pro from ProductItem pro")
    List<ProductItem> selectAllProductItems();

    /**
     * Select all product items and fetch its configuration
     * @return List of product items with configuration
     */
    @Query(value = "select distinct pro from ProductItem pro join fetch pro.productConfigurations pc")
    List<ProductItem> selectAllProductItemAndFetchConfig();

    /**
     * Select a product item by id and fetch its configuration
     * @param id id of a product item
     * @return Product item with its configuration
     */
    @Query(value = "select distinct pro from ProductItem pro join fetch pro.productConfigurations pc where pro.id = :id")
    ProductItem selectProductItemAndFetchConfigById(long id);

//    @Query(value = "select distinct pro from ProductItem pro join fetch pro.productConfigurations pc where pro.id = :id")
//    ProductItem selectProductItemAndFetchConfigById(@Param("id") long id);
//
//    @Query(value = "select distinct pit from :productItem pit join fetch pit.productImages img")
//    ProductItem selectProductItemAndFetchImages(@Param("productItem") ProductItem productItem);

    /**
     * Select a product from product items by id of a product item
     * @param id id of a product item
     * @return product of a product item
     */
    @Query(value = "select pro.product from ProductItem pro where pro.id = :id")
    Product selectProductByProductItemId(Long id);

    /**
     * Select all product items of a product by product id
     * @param id id of a product
     * @return List of product item
     */
    @Query(value = "select pro from ProductItem pro where pro.product.id = :id")
    List<ProductItem> selectAllProductItemsByProductId(Long id);

    @Query(value = "select distinct pro from ProductItem pro join fetch pro.productConfigurations where pro.product.id = :id")
    List<ProductItem> selectAllProductItemsAndFetchConfigurationByProductId(Long id);
}
