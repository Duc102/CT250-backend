package ecommerce.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="shop_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUser siteUser;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Column(name="order_date")
    @CreationTimestamp
    private LocalDateTime dateCreate;

    @Column(name="update_date")
    @UpdateTimestamp
    private LocalDateTime dateUpdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shopOrder")
    List<OrderLine> orderLines;

    @Column(name="order_total")
    private float orderTotal;

}
