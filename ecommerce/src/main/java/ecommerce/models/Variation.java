package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="variation")
@AllArgsConstructor
@Getter
@Setter
public class Variation {
    @Id
    private long id;
    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @JsonManagedReference
//    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variation")
    private List<VariationOption> variationOption;

    public Variation(){
        id = 0;
        name = "New variation";
        productCategory = null;
    }
}
