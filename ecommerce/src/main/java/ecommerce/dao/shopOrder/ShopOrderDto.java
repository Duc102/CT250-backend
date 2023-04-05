package ecommerce.dao.shopOrder;

import ecommerce.models.Address;
import ecommerce.models.Payment;
import ecommerce.models.ShoppingCartItem;
import ecommerce.models.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopOrderDto {
    private SiteUser siteUser;
    private List<ShoppingCartItem> shoppingCartItems;
    private Address address;
    private Payment payment;
}
