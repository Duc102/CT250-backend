package ecommerce.services;

import ecommerce.models.Address;
import ecommerce.models.SiteUser;
import ecommerce.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    SiteUserRepository siteUserRepository;

    @Autowired
    AddressService addressService;

    /**
     *Save a new user in the database and return this user with the id attribute provided by the database
     * @param user
     * @return The user was saved in database
     */
    public SiteUser saveUser(SiteUser user){
        user = siteUserRepository.save(user);
        return user;
    }

    /**
     * Find all addresses of a site user by id of the site user
     * @param id
     * @return Set of addresses of the user.
     */
    public Set<Address> findAllAddressOfUser(Long id) {
        SiteUser user = siteUserRepository.findSiteUserByIdAndFetchAllAddress(id);
        Set<Address> addresses = new HashSet<>();
        user.getAddresses().forEach((address)->{
            addresses.add(address.getAddress());
        });
        return addresses;
    }

    public void addNewAddressToSiteUser(Long userId, Address address){
        address = addressService.saveAddress(address);

        SiteUser user =  siteUserRepository.findSiteUserByIdAndFetchAllAddress(9L);
        user.addAddress(address, true);
        saveUser(user);
        System.out.println(user.getAddresses().size());
    }
}
