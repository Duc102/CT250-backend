package ecommerce.services;

import ecommerce.dao.shopOrder.*;
import ecommerce.models.*;
import ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ShopOrderService {
    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    public ShopOrder createNewShopOrder(ShopOrderDto shopOrderDto){
        SiteUser siteUser = shopOrderDto.getSiteUser();
        List<ShoppingCartItem> shoppingCartItems = shopOrderDto.getShoppingCartItems();
        Address address = shopOrderDto.getAddress();
        Payment payment = shopOrderDto.getPayment();

        ShopOrder newShopOrder = new ShopOrder();
        newShopOrder.setSiteUser(siteUser);
        newShopOrder.setShippingAddress(address);
        newShopOrder.setPayment(payment);
        OrderStatus status = orderStatusRepository.findById(1L).get();
        newShopOrder.setOrderStatus(status);

        List<OrderLine> orderLines = new ArrayList<>();
        AtomicReference<Float> total = new AtomicReference<>((float) 0);
        shoppingCartItems.forEach(shoppingCartItem -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setShopOrder(newShopOrder);
//            ProductItem productItem = shoppingCartItem.getProductItem();
            ProductItem productItem = productItemRepository.findById(shoppingCartItem.getProductItem().getId()).get();
//            orderLine.setProductItem(shoppingCartItem.getProductItem());
            orderLine.setProductItem(productItem);
            productItem.setQtyInStock(productItem.getQtyInStock()-shoppingCartItem.getQty());

            productItemRepository.save(productItem);
            orderLine.setQty(shoppingCartItem.getQty());
            orderLines.add(orderLine);
            total.updateAndGet(v -> (float) (v + shoppingCartItem.getQty() * shoppingCartItem.getProductItem().getPrice()));
        });
        newShopOrder.setOrderLines(orderLines);
        newShopOrder.setOrderTotal(total.get());
//        Clear shopping cart
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUserSite(siteUser.getId());
        shoppingCartService.clearShoppingCartItem(shoppingCart);
        return shopOrderRepository.save(newShopOrder);
    }
    /***
     * Find all shop order.
     * @return
     */
    public List<ShopOrder> findAllShopOrders(){
        return shopOrderRepository.findAll();
    }

    public List<ShopOrder> findAllShopOrdersByUserId(Long id){
        return shopOrderRepository.selectByUserId(id);
    }

    /**
     * Find shop order by id.
     * @param shopOrderId
     * @return
     */
    public ShopOrder findShopOrderById(Long shopOrderId){
        return shopOrderRepository.findById(shopOrderId).orElse(null);
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
     * Get shop order by order date.
     * @param dateTime
     * @return
     */
    public List<ShopOrder> findShopOrderByCreatedDate(LocalDateTime dateTime){
        LocalDateTime date = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0, 0);
        return shopOrderRepository.selectByCreatedDate(date);
    }

    public List<ShopOrder> findTodayShopOrders(){
        LocalDateTime today =LocalDateTime.now();
        LocalDateTime beginOfDay = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0, 0);
        return shopOrderRepository.selectByCreatedDate(beginOfDay);
    }

    public Float findTodayEarning(){
        LocalDateTime today =LocalDateTime.now();
        LocalDateTime beginOfDay = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0, 0);
        return shopOrderRepository.todayEarning(beginOfDay);
    }

    /**
     * Find all order status
     * @return
     */
    public List<OrderStatus> findAllOrderStatus(){
        return orderStatusRepository.findAll();
    }

    /**
     * Update shop order status by shop order id and status id
     * @param shopOrderId
     * @param orderStatusId
     * @return
     */
    public ShopOrder updateShopOrderStatus(long shopOrderId, long orderStatusId) {
        ShopOrder order = shopOrderRepository.findById(shopOrderId).get();
        OrderStatus status = orderStatusRepository.findById(orderStatusId).get();
        order.setOrderStatus(status);
        return shopOrderRepository.save(order);
    }

    public boolean deleteShopOrder(long id){
        shopOrderRepository.deleteById(id);
        return true;
    }

    public List<RevenueDao> findRevenue(int year){
        return shopOrderRepository.selectRevenue(year);
    }

    public List<TopTenProItDto> findTopTens(int month, int year){
        List<TopTenProItDao> topTenDaos = shopOrderRepository.selectTopTenProIts(month, year);
        List<TopTenProItDto> topTenProItDtos = new ArrayList<>();
        topTenDaos.forEach(topTen ->{
            ProductItem productItem = productItemRepository.findById(topTen.getProductItemId()).get();
            Product product = productItemRepository.selectProductByProductItemId(productItem.getId());
            TopTenProItDto topTenProItDto = new TopTenProItDto(product, productItem, topTen.getCount());
            topTenProItDtos.add(topTenProItDto);
        });
        return topTenProItDtos;
    }

    public List<ProductItem> findTopTenProductItem(int month, int year){
        List <ProductItem> result = new ArrayList<>();
        List <Long> id = shopOrderRepository.selectTopTenProductItem(month, year);
        id.forEach(longId -> {
            ProductItem productItem = productItemRepository.findById(longId).get();
            result.add(productItem);
        });
        return result;
    }

    public InventoryDao findInventory(){
        LocalDateTime dateTime = LocalDateTime.now();
        return shopOrderRepository.getInventory(dateTime.getMonthValue(), dateTime.getYear());
    }
}
