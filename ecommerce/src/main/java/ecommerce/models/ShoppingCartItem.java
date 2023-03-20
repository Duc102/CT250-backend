package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="shopping_cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartItem {
    @EmbeddedId
    private ShoppingCartItemId id;

    @OneToOne
    @JoinColumn(name="product_item_id", updatable = false, insertable = false, referencedColumnName = "id")
    private ProductItem productItem;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="cart_id", updatable = false, insertable = false, referencedColumnName = "id")
    private ShoppingCart shoppingCart;

    @Column(name="qty")
    private int qty;
}
