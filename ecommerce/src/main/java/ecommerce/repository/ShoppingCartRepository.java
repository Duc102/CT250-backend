package ecommerce.repository;

import ecommerce.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query(value="select sc from ShoppingCart sc where sc.siteUser.id = :id")
    ShoppingCart selectShoppingCarBySiteUserId(Long id);
}
