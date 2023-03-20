package ecommerce.services;


import ecommerce.models.ShoppingCart;
import ecommerce.models.ShoppingCartItem;
import ecommerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;


    public ShoppingCart findShoppingCartById(long id){
        return shoppingCartRepository.findById(id).get();
    }
}
