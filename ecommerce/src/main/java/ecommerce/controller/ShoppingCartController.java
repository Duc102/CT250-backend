package ecommerce.controller;

import ecommerce.models.ShoppingCart;
import ecommerce.models.ShoppingCartItem;
import ecommerce.models.ShoppingCartItemId;
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

    @GetMapping("/siteUser/{id}")
    public ShoppingCart getShoppingCartBySiteUser(@PathVariable long id){
        return shoppingCartService.findShoppingCartByUserSite(id);
    }

    @PostMapping("/addShoppingCartItem")
    public ShoppingCartItem addShoppingCarItem(@RequestBody ShoppingCartItem shoppingCartItem){
        return shoppingCartService.addShoppingCartItem(shoppingCartItem);
    }

    @PostMapping("/updateShoppingCartItem")
    public boolean updateShoppingCarItem(@RequestBody ShoppingCartItem shoppingCartItem){
        return shoppingCartService.updateShoppingCartItem(shoppingCartItem);
    }

    @PostMapping("/deleteShoppingCartItem")
    public boolean deleteShoppingCarItem(@RequestBody ShoppingCartItem shoppingCartItem){
        return shoppingCartService.deleteShoppingCartItem(shoppingCartItem);
    }
}
