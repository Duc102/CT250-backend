package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="variation_option")
@AllArgsConstructor
@Getter
@Setter
public class VariationOption {
    @Id
    private long id;
    @Column(name = "value")
    private String value;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "variation_id")
    private Variation variation;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "variationOption")
    List<ProductConfiguration> productConfigurations;

    public VariationOption (){
        id = 0;
        value = "New variation option";
        variation = null;
    }
}
