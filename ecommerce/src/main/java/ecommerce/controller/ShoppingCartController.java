package ecommerce.controller;

import ecommerce.models.ShoppingCart;
import ecommerce.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCart")
@CrossOrigin(origins = "http://localhost:3000")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{id}")
    public ShoppingCart getShoppingCartById(@PathVariable long id){
        return shoppingCartService.findShoppingCartById(id);
    }
}
