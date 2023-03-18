package ecommerce.repository;

import ecommerce.models.ShopOrder;
import ecommerce.models.SiteUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopOrderRepositoryTest {
    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired private SiteUserRepository siteUserRepository;
    @Test
    public void createShopOrder(){
        ShopOrder order = shopOrderRepository.findById(1L).get();
        System.out.println(order.getSiteUser().getEmailAddress());
        System.out.println(order.getShippingAddress().getCity());
        System.out.println(order.getOrderTotal());
        System.out.println(order.getOrderStatus().getStatus());
    }
}