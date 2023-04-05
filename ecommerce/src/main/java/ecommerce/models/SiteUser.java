package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "site_user")
@AllArgsConstructor
@Getter
@Setter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name ="phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<UserPayAssociation> payments;

    @JsonManagedReference
    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private Set<UserAddressAssociation> addresses;



    public SiteUser() {
        long numberCode = System.nanoTime();
        emailAddress = "user"+numberCode+"@gmail.com";
        phoneNumber = String.valueOf(numberCode).substring(0, 9);
        password = phoneNumber;
        addresses = new HashSet<>();
        payments = new ArrayList<>();
    }

    public UserAddressAssociation addAddress(Address address, boolean isDefault){
        UserAddressAssociation association = new UserAddressAssociation();
        association.setAddress(address);
        association.setSiteUser(this);
        association.setDefault(isDefault);
        this.addresses.add(association);
        address.getSiteUsers().add(association);
        return association;
    }
}
