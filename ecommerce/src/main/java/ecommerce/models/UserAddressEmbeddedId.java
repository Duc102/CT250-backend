package ecommerce.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserAddressEmbeddedId implements Serializable {
    @Column(name="user_id")
    private  long user;
    @Column(name="address_id")
    private long address;
}