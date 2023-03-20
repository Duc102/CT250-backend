package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="shopping_cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private SiteUser siteUser;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart", orphanRemoval = true)
    private List<ShoppingCartItem> shoppingCartItems;
}
