package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_configuration")
@AllArgsConstructor
@Getter
@Setter
public class ProductConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "variation_option_id")
    private VariationOption variationOption;

    public ProductConfiguration(){
        productItem = null;
        variationOption = null;
    }
}
