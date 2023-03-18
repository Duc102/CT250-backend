package ecommerce.repository;

import ecommerce.models.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {

}
