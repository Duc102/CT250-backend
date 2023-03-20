package ecommerce.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShoppingCartItemId implements Serializable {
    @Column(name="cart_id")
    private long shoppingCartId;
    @Column(name="product_item_id")
    private long productItemId;
}
