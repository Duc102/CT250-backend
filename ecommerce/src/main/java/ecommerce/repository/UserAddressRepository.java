package ecommerce.repository;

import ecommerce.models.UserAddressAssociation;
import ecommerce.models.UserAddressEmbeddedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddressAssociation, UserAddressEmbeddedId> {
}
