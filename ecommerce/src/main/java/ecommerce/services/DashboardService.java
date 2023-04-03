package ecommerce.services;

import ecommerce.dao.shopOrder.InventoryDao;
import ecommerce.dao.shopOrder.RevenueDao;
import ecommerce.dao.shopOrder.TopTenProItDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private ShopOrderService shopOrderService;

    public List<RevenueDao> getRevenue(int year){
        return shopOrderService.findRevenue(year);
    }

    public List<TopTenProItDto> getTopTenProductItems(int month, int year){
        return shopOrderService.findTopTens(month, year);
    }

    public InventoryDao getInventory(){
        return shopOrderService.findInventory();
    }
}
