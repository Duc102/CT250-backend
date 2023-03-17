package ecommerce.repository;

import ecommerce.models.Address;
import ecommerce.models.SiteUser;
import ecommerce.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
//    @Query(value="select a.city as city from user_address ua join Address a on ua.address_id = a.id where ua.user_id =:userId", nativeQuery = true)
//    List<UserAddressDTO> findByUserId(long userId);
//
//    @Query(value="select su from SiteUser su join fetch su.addresses")
//    List<SiteUser> findByUserIdNotNative();
//
//    @Query(value = "select user from SiteUser user join user.addresses address where address.city=:city")
//    List<SiteUser> findUserWhoHaveAddressAtCity(String city);
//
//    @Query(value = "select t from Address t where t.id = 8")
//    List<Address> selectAll();

    @Query(value = "select ad from Address ad left join fetch ad.siteUsers where ad.id = :id")
    Address findByIdAndFetch(Long id);
}
