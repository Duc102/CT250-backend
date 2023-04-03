package ecommerce.repository;

import ecommerce.dao.shopOrder.InventoryDao;
import ecommerce.dao.shopOrder.RevenueDao;
import ecommerce.dao.shopOrder.TopTenProItDao;
import ecommerce.models.OrderLine;
import ecommerce.models.ProductItem;
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

    @Query(value = "select case when sum(s.orderTotal) is null then 0.0 else sum(s.orderTotal) end as total from ShopOrder s where s.dateCreate = :dateTime")
    Float todayEarning(LocalDateTime dateTime);

    @Query(value = "select r.month month, count(r.month) count, sum(r.order_total) total from ( "
            +"select month(order_date) month, order_total "
            +"from shop_order "
            +"where year(order_date) = :year ) as r "
            +"group by r.month", nativeQuery = true)
    List<RevenueDao> selectRevenue(int year);

    @Query(value = "select top 10 pi.id productItemId, count(pi.id) count "
            + "from shop_order so join order_line ol on so.id = ol.shop_order_id join product_item pi on pi.id = ol.product_item_id "
            + "where month(so.order_date) = :month and year(so.order_date) = :year "
            + "group by pi.id "
            + "order by count desc",nativeQuery = true)
    List<TopTenProItDao> selectTopTenProIts(int month, int year);

    @Query(value = "select top 10 pi.id "
            + "from shop_order so join order_line ol on so.id = ol.shop_order_id join product_item pi on pi.id = ol.product_item_id "
            + "where month(so.order_date) = :month and year(so.order_date) = :year "
            + "group by pi.id "
            + "order by count(pi.id) desc",nativeQuery = true)
    List<Long> selectTopTenProductItem(int month, int year);

    @Query(value = "select sum(st2.qty_in_stock) inventory, sum(st2.sold) sold from ( "
                        + "select pi.id, pi.qty_in_stock, sum(case when st1.sold is null then 0 else st1.sold  end) sold from product_item pi left join ( "
                                + "select ol.product_item_id as id,  ol.qty sold from shop_order so join order_line ol on so.id = ol.shop_order_id "
                                + "where month(so.order_date) = :month and year(so.order_date) = :year) st1 "
                        + "on pi.id = st1.id "
                        + "group by pi.id, pi.qty_in_stock) st2"
            ,nativeQuery = true)
    InventoryDao getInventory(int month, int year);

}
