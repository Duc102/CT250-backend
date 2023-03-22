package ecommerce.repository;

import ecommerce.models.ShoppingCartItem;
import ecommerce.models.ShoppingCartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, ShoppingCartItemId> {

    @Query(value = "select it from ShoppingCartItem it where it.id.shoppingCartId = :shoppingCartId and it.id.productItemId = :productItemId")
    ShoppingCartItem selectShoppingCartItemById(Long shoppingCartId, Long productItemId);
}
