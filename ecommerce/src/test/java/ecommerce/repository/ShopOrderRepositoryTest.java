package ecommerce.repository;

import ecommerce.dao.shopOrder.RevenueDao;
import ecommerce.models.*;
import ecommerce.services.ShopOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShopOrderRepositoryTest {
    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired private SiteUserRepository siteUserRepository;

    @Autowired private ProductItemRepository productItemRepository;

    @Autowired private AddressRepository addressRepository;

    @Autowired private OrderStatusRepository orderStatusRepository;

    @Autowired private ShopOrderService shopOrderService;
    @Test
    public void getShopOrder(){
        ShopOrder order = shopOrderRepository.selectShopOrderAndFetchOrderLines(1L);
        List<OrderLine> or = shopOrderRepository.selectOrderLine(1L);
        System.out.println(or.size());
        System.out.println(order.getSiteUser().getEmailAddress());
        System.out.println(order.getShippingAddress().getCity());
        System.out.println(order.getOrderLines().size());
        System.out.println(order.getOrderTotal());
        System.out.println(order.getOrderStatus().getStatus());
    }

    @Test
    public void createShopOrder(){
        ProductItem productItem = productItemRepository.findById(7L).get();
        SiteUser user = siteUserRepository.findById(1L).get();
        Address address = addressRepository.findById(1L).get();
        OrderStatus status = orderStatusRepository.findById(1L).get();
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setSiteUser(user);
        shopOrder.setShippingAddress(address);
        shopOrder.setOrderTotal(productItem.getPrice());
        shopOrder.setOrderStatus(status);
        OrderLine orderLine = new OrderLine();
        orderLine.setProductItem(productItem);
        orderLine.setQty(3);
        orderLine.setShopOrder(shopOrder);
        List<OrderLine> orderLines = shopOrder.getOrderLines();
        orderLines.add(orderLine);
        shopOrderRepository.save(shopOrder);
    }

    @Test
    public void function(){
        SiteUser s = siteUserRepository.findById(1L).get();
//        System.out.println(s.getPayments().size());

    }

}