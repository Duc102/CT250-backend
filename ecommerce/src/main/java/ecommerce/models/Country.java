package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
}
