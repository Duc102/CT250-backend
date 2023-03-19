package ecommerce.services;

import ecommerce.models.OrderStatus;
import ecommerce.models.ShopOrder;
import ecommerce.repository.OrderStatusRepository;
import ecommerce.repository.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopOrderService {
    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    /***
     * Find all shop order.
     * @return
     */
    public List<ShopOrder> findAllShopOrder(){
        return shopOrderRepository.findAll();
    }

    /**
     * Find shop order by id.
     * @param shopOrderId
     * @return
     */
    public ShopOrder findShopOrderById(Long shopOrderId){
        return shopOrderRepository.findById(shopOrderId).get();
    }

    /**
     * Find shop order by order status id
     * @param orderStatusId
     * @return
     */
    public List<ShopOrder> findShopOrderByOrderStatus(Long orderStatusId){
        return shopOrderRepository.selectByOrderStatus(orderStatusId);
    }

    /**
     * Find all order status
     * @return
     */
    public List<OrderStatus> findAllOrderStatus(){
        return orderStatusRepository.findAll();
    }
}