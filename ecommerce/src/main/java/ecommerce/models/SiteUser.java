package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "site_user")
@AllArgsConstructor
@Getter
@Setter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private long id;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name ="phone_number")
    private String phoneNumber;
    @Column(name = "password")
    private String password;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_address",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "address_id")
//    )
//    private Set<Address> addresses = new HashSet<>();
    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private Set<UserAddressAssociation> addresses;

    public SiteUser() {
        long numberCode = System.nanoTime();
        emailAddress = "user"+numberCode+"@gmail.com";
        phoneNumber = String.valueOf(numberCode).substring(0, 9);
        password = phoneNumber;
        addresses = new HashSet<>();
    }

    public UserAddressAssociation addAddress(Address address, boolean isDefault){
        UserAddressAssociation association = new UserAddressAssociation();
        association.setAddressId(address.getId());
        association.setUserId(this.id);
        association.setAddress(address);
        association.setSiteUser(this);
        association.setDefault(isDefault);
        this.addresses.add(association);
        address.getSiteUsers().add(association);
        return association;
    }
}
