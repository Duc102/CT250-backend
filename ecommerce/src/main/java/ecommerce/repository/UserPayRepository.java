package ecommerce.repository;

import ecommerce.models.UserPayAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPayRepository extends JpaRepository<UserPayAssociation, Long> {
}
