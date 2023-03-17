package ecommerce.repository;

import ecommerce.dao.productCategory.ProductCategoryDao;
import ecommerce.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository <ProductCategory, Long>{
    @Query(value = "select pc from ProductCategory pc where pc.parentCategory=null")
    List<ProductCategory> selectCategoryZeroLevel();

    @Query(value = "select pc.id as id, pc.categoryName as categoryName from ProductCategory pc where pc.parentCategory.id = :id")
    List<ProductCategoryDao> selectChildrenOfCategory(Long id);

    @Query(value = "with cte as ("
            + "select pc.id from product_category pc where pc.id = :id "
            + "union all "
            + "select pc.id from product_category pc join cte on cte.id = pc.parent_category_id"
            + ") "
            + "select id from cte"
            ,nativeQuery = true)
    List<Integer> selectFamilyTreeByCategoryId(Long id);
}


