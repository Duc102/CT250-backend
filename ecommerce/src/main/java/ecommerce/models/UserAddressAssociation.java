package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
//@IdClass(UserAddressAssociationId.class) // => This is @IdClass.
@Table(name = "user_address")
@AllArgsConstructor
@Getter
@Setter
public class UserAddressAssociation {
//    @Id                                 //
//    @Column(name = "user_id")           //
//    private long userId;                //
//                                        // => This is @IdClass
//    @Id                                 //
//    @Column(name = "address_id")        //
//    private long addressId;             //

    @EmbeddedId
    private UserAddressEmbeddedId id;

    @Column(name = "is_default")
    private boolean isDefault;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false, referencedColumnName = "id")
    private SiteUser siteUser;

    @ManyToOne
    @JoinColumn(name = "address_id" , updatable = false, insertable = false, referencedColumnName = "id")
    private Address address;

    public UserAddressAssociation(){
        isDefault = false;
    }
}
