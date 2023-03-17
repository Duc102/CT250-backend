package ecommerce.models;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private long id;
    @Column(name = "unit_number")
    private int unitNumber;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "first_address_line")
    private String firstAddressLine;
    @Column(name = "second_address_line")
    private String secondAddressLine;
    @Column(name = "city")
    private String city;
    @Column(name = "region")
    private String region;
    @Column(name = "postal_code")
    private String postalCode;

//    @ManyToMany(mappedBy = "addresses")
//    private Set<SiteUser> siteUsers = new HashSet<>();

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private Set<UserAddressAssociation> siteUsers;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Address(){
        unitNumber = 100;
        streetNumber = "3/2";
        firstAddressLine = "Xuân Khánh";
        secondAddressLine = "Ninh Kiều";
        city = "Cần Thơ";
        region = "Nam bộ";
        postalCode = "123";
        country = new Country("VN", "Việt Nam");
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
        association.setAddressId(this.id);
        association.setUserId(siteUser.getId());
        association.setDefault(isDefault);
        this.siteUsers.add(association);
        siteUser.getAddresses().add(association);
        return association;
    }
}
