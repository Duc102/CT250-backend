package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="product_image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name="productItem_id")
    @JsonBackReference
    private ProductItem productItem;

    @Override
    public boolean equals(Object obj){
        if(this.getId() == ((ProductImage)obj).getId())
            return true;
        else {
            return false;
        }
    }
}
