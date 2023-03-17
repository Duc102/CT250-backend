package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="product_category")
@AllArgsConstructor
@Getter
@Setter
public class ProductCategory {
    @Id
    private long id;
    @Column(name="category_name")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name="parent_category_id")
    private ProductCategory parentCategory;

    public ProductCategory(){
        categoryName = "New Category";
    }
}
