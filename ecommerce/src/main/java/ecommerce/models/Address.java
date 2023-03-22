package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="address")
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_address_line")
    private String firstAddressLine;
    @Column(name = "second_address_line")
    private String secondAddressLine;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_code")
    private String postalCode;

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private Set<UserAddressAssociation> siteUsers;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Address(){
        firstAddressLine = "Xuan Khanh";
        secondAddressLine = "Ninh Kieu";
        city = "Can Tho";
        postalCode = "9000";
        siteUsers = new HashSet<>();
    }
//    public void addUser(SiteUser user){
//        siteUsers.add(user);
//
//    }

    public UserAddressAssociation addSiteUser(SiteUser siteUser, boolean isDefault){
        UserAddressAssociation association = new UserAddressAssociation();
        association.setSiteUser(siteUser);
        association.setAddress(this);
//        association.setAddressId(this.id);
//        association.setUserId(siteUser.getId());
        association.setDefault(isDefault);
        this.siteUsers.add(association);
        siteUser.getAddresses().add(association);
        return association;
    }
}
