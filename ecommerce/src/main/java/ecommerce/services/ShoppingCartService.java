package ecommerce.services;


import ecommerce.models.ShoppingCart;
import ecommerce.models.ShoppingCartItem;
import ecommerce.repository.ShoppingCartItemRepository;
import ecommerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCart findShoppingCartById(long id){
        return shoppingCartRepository.findById(id).get();
    }

    public ShoppingCart findShoppingCartByUserSite(long userSiteId) {
        return shoppingCartRepository.selectShoppingCarBySiteUserId(userSiteId);
    }
    public ShoppingCartItem addShoppingCartItem(ShoppingCartItem shoppingCartItem){
        ShoppingCartItem newShoppingCartItem = new ShoppingCartItem();
        newShoppingCartItem.setId(shoppingCartItem.getId());
        newShoppingCartItem.setQty(shoppingCartItem.getQty());
        ShoppingCartItem existed = shoppingCartItemRepository.selectShoppingCartItemById(shoppingCartItem.getId().getShoppingCartId(),
                                                                                        shoppingCartItem.getId().getProductItemId());
        if(existed != null){
            existed.setQty(existed.getQty() + shoppingCartItem.getQty());
            return shoppingCartItemRepository.save(existed);
        }
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    public boolean updateShoppingCartItem(ShoppingCartItem shoppingCartItem){
        ShoppingCartItem newShoppingCartItem = new ShoppingCartItem();
        newShoppingCartItem.setId(shoppingCartItem.getId());
        newShoppingCartItem.setQty(shoppingCartItem.getQty());
        shoppingCartItemRepository.save(shoppingCartItem);
        return true;
    }

    public boolean deleteShoppingCartItem(ShoppingCartItem shoppingCartItem){
        shoppingCartItemRepository.delete(shoppingCartItem);
        return true;
    }
}
