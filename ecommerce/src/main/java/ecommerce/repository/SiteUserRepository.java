package ecommerce.repository;

import ecommerce.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {

    @Query(value = "select su from SiteUser su left join fetch su.addresses where su.id = :id")
    SiteUser findSiteUserByIdAndFetchAllAddress(Long id);

    @Query(value = "select su from SiteUser su where su.emailAddress = :emailAddress")
    SiteUser findSiteUserByEmailAddress(String emailAddress);

    @Query(value="select su from SiteUser su where su.emailAddress = :emailAddress and su.password = :password")
    SiteUser findSiteUserByEmailAddressAndPassword(String emailAddress, String password);

    @Query(value="select count(su) from SiteUser su")
    int countSiteUser();

    @Query(value="select su from SiteUser su where lower(su.name) like lower(concat('%', :name, '%'))")
    List<SiteUser> findSiteUsersByName(String name);
}
