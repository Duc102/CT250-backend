package ecommerce.models;

import java.io.Serializable;

public class UserAddressAssociationId implements Serializable {
    private long userId;
    private long addressId;
    public int hashCode(){
        return (int)(userId + addressId);
    }
    public boolean equals(Object object) {
        if(object instanceof UserAddressAssociationId){
            UserAddressAssociationId other = (UserAddressAssociationId) object;
            return ((other.userId == this.userId) && (other.addressId == this.addressId));
        }
        return false;
    }
}
