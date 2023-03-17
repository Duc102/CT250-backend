package ecommerce.repository;

import ecommerce.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
//    @Query(value="select su from SiteUser su left join fetch su.addresses where su.id = :id")
//    SiteUser findSiteUserByIdAndFetch(Long id);

    @Query(value = "select su from SiteUser su left join fetch su.addresses where su.id = :id")
    SiteUser findSiteUserByIdAndFetchAllAddress(Long id);
}
