package ecommerce.controller;

import ecommerce.dao.shopOrder.RevenueDao;
import ecommerce.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/revenue/{year}")
    public List<RevenueDao> getRevenue(@PathVariable int year){
        return dashboardService.getRevenue(year);
    }
}
