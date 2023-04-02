package ecommerce.controller;

import ecommerce.dao.shopOrder.ShopOrderDto;
import ecommerce.dao.shopOrder.TopTenProItDto;
import ecommerce.models.*;
import ecommerce.services.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/administrator/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class ShopOrderController {
    @Autowired
    private ShopOrderService shopOrderService;


    @PostMapping("/createNewShopOrder")
    public ShopOrder createNewShopOrder(@RequestBody ShopOrderDto shopOrderDto){
        return shopOrderService.createNewShopOrder(shopOrderDto);

    }
    /**
     * Get all product shop orders
     * @return
     */
    @GetMapping ("/getAllShopOrders")
    public List<ShopOrder> getAllShopOrders(){
        return shopOrderService.findAllShopOrders();
    }

    /**
     * Get shop orders by order status
     * @param id
     * @return
     */
    @GetMapping("/getShopOrders/status/{id}")
    public List<ShopOrder> getShopOrderByOrderStatus(@PathVariable long id){
        return shopOrderService.findShopOrderByOrderStatus(id);
    }

    /**
     * Get shop order by shop order id
     * @param id
     * @return
     */
    @GetMapping("/getShopOrder/{id}")
    public ShopOrder getShopOrderById(@PathVariable long id){
        return shopOrderService.findShopOrderById(id);
    }

    @PostMapping("/getShopOrder/dateTime")
    public List<ShopOrder> getShopOrderByDateTime(@RequestBody LocalDateTime dateTime){
        return shopOrderService.findShopOrderByCreatedDate(dateTime);
    }

    @GetMapping("/todayShopOrders")
    public List<ShopOrder> getTodayShopOrders(){
        return shopOrderService.findTodayShopOrders();
    }

    @GetMapping("/todayEarning")
    public Float todayEarning(){
        return shopOrderService.findTodayEarning();
    }

    @GetMapping("/topTenProductItems/{month}/{year}")
    public List<ProductItem> getTopTenProductItem(@PathVariable int month, @PathVariable int year){
        return shopOrderService.findTopTenProductItem(month, year);
    }

    @GetMapping("/getAllOrderStatus")
    public List<OrderStatus> getAllOrderStatus(){
        return shopOrderService.findAllOrderStatus();
    }


    @GetMapping("/quickUpdate/{orderId}/{statusId}")
    public ShopOrder quickUpdateShopOrderStatus(@PathVariable long orderId, @PathVariable long statusId){
        return shopOrderService.updateShopOrderStatus(orderId, statusId);
    }

    @GetMapping("/quickDeleteOrder/{id}")
    public boolean quickDeleteShoOrder(@PathVariable long id){
        return shopOrderService.deleteShopOrder(id);
    }
}
