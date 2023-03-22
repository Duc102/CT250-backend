package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartItemId implements Serializable {
    @Column(name="cart_id")
    private long shoppingCartId;
    @Column(name="product_item_id")
    private long productItemId;
}
