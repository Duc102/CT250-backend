package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_item")
@AllArgsConstructor
@Getter
@Setter
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="sku")
    private String sku;
    @Column(name = "qty_in_stock")
    private int qtyInStock;
    @Column(name = "price")
    private float price;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productItem", orphanRemoval= true)
    private List<ProductConfiguration> productConfigurations;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL ,/* fetch = FetchType.EAGER,*/ mappedBy = "productItem", orphanRemoval = true)
    private List<ProductImage> productImages;

    public ProductItem(){
        id = 0;
        sku = "00000000";
        qtyInStock = 0;
        price = 0;
        product = null;
    }
}
