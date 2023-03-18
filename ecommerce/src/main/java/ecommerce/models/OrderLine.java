package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_line")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "shop_order_id")
    private ShopOrder shopOrder;

    @Column(name="qty")
    private int qty;
}
