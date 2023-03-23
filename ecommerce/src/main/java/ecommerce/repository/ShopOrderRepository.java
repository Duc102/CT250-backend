package ecommerce.repository;

import ecommerce.dao.shopOrder.RevenueDao;
import ecommerce.models.OrderLine;
import ecommerce.models.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {

    @Query(value = "select s from ShopOrder s join fetch s.orderLines o where s.id = :shopOrderId")
    ShopOrder selectShopOrderAndFetchOrderLines(Long shopOrderId);

    @Query(value = "select s.orderLines from ShopOrder s join s.orderLines o where s.id = :shopOrderId")
    List<OrderLine> selectOrderLine(Long shopOrderId);

    @Query(value="select s from ShopOrder s where s.orderStatus.id = :id")
    List<ShopOrder> selectByOrderStatus(Long id);

    @Query(value="select s from ShopOrder s where s.dateCreate = :dateTime")
    List<ShopOrder> selectByCreatedDate(LocalDateTime dateTime);

    @Query(value = "select sum(s.orderTotal) from ShopOrder s where s.dateCreate = :dateTime")
    float todayEarning(LocalDateTime dateTime);

    @Query(value = "select r.month month, count(r.month) count, sum(r.order_total) total from ( "
            +"select month(order_date) month, order_total "
            +"from shop_order "
            +"where year(order_date) = :year ) as r "
            +"group by r.month", nativeQuery = true)
    List<RevenueDao> selectRevenue(int year);
}
