package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "product_image")
    private String productImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductItem> productItems;

    public Product(){
        id = 0;
        name = "New Product";
        description = "The best product";
        productImage = "This image for the product";
        productCategory = null;
    }
}
