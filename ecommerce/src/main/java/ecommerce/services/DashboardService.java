package ecommerce.services;

import ecommerce.dao.shopOrder.RevenueDao;
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
}
