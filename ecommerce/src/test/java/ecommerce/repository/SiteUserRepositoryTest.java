package ecommerce.repository;

import ecommerce.models.Address;
import ecommerce.models.Country;
import ecommerce.models.SiteUser;
import ecommerce.services.AddressService;
import ecommerce.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class SiteUserRepositoryTest {
    @Autowired
    private SiteUserRepository siteUserRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AddressRepository addressRepository;
//    @Test
//    @Transactional
//    public void functionSiteUser(){
//        SiteUser user = siteUserRepository.findById(Long.valueOf(1)).get();
//        Address address = new Address();
//        address.setCity("Bến Tre");
//        address.setRegion("Miền Nam");
//        address.setCountry(new Country("VN", "Việt Nam"));
//        user.addAddress(address);
//        siteUserRepository.save(user);
//    }
//
//    @Test
//    public void functionAddAddress(){
//        SiteUser user = new SiteUser();
//        user.setId(4L);
//        user.setEmailAddress("link@gmail.com");
//        user.setPhoneNumber("090009898");
//        user.setPassword("54444445");
//        Address address = new Address();
//        address.setUnitNumber(130);
//        address.setStreetNumber("30/4");
//        address.setFirstAddressLine("Vĩnh Bình");
//        address.setSecondAddressLine("Chợ Lách");
//        address.setCity("Bến Tre");
//        address.setRegion("3");
//        address.setCountry(countryRepository.findById("VN").get());
//        user.addAddress(address);
//        siteUserRepository.save(user);
//    }
//    @Test
//    public void functionTwo(){
//        Country country = countryRepository.findById("VN").get();
//        System.out.println(country.getName());
//    }
//
//    @Test
//    public void functionAddress(){
//        Address address = addressRepository.findById(Long.valueOf(0)).get();
//        System.out.println(address.getCity());
//        System.out.println(address.getStreetNumber());
//        System.out.println(address.getCountry().getName());
//
//    }
//    @Test
//    public void functionQueryAddressByUserId(){
//        List<UserAddressDTO> addresses = addressRepository.findByUserId(4l);
//        addresses.forEach((address)-> System.out.println(address.getCity()));
////        List<Address> addresses = addressRepository.selectAll();
////        System.out.println(addresses.size());
////        addresses.forEach((address -> System.out.println(address.getCity())));
//    }
//    @Test
//    public void functionFetchUser(){
//        List<SiteUser> siteUsers = addressRepository.findByUserIdNotNative();
//        System.out.println(siteUsers.size());
//        siteUsers.forEach((user) -> {
//            user.getAddresses().forEach((address)->System.out.println(address.getCity()));
//        });
//    }
//
//    @Test
//    public void functionFindUserByCityDontFetch(){
//        List<SiteUser> siteUsers = addressRepository.findUserWhoHaveAddressAtCity("Bến Tre");
//        System.out.println(siteUsers.size());
//        siteUsers.forEach((siteUser -> System.out.println(siteUser.getEmailAddress())));
//    }
//
//    @Test
//    public void functionAddNewAddress(){
//        Address address = new Address();
//        address.setUnitNumber(120);
//        address.setStreetNumber("1/5");
//        address.setRegion("Miền Bắc");
//        address.setFirstAddressLine("Phường Chèo");
//        address.setSecondAddressLine("Hội An");
//        address.setCity("Hà Nội");
//        Country country = countryRepository.findById("VN").get();
//        address.setCountry(country);
//        addressRepository.save(address);
//    }
//
//    @Test
//    public void createNewUserWithFullInfo(){
//        SiteUser user = new SiteUser();
//        user.setPassword("888");
//        user.setEmailAddress("lv@gmail.com");
//        user.setPhoneNumber("1233");
//        siteUserRepository.save(user);
//
//        Address address = addressRepository.findById(9L).get();
//
//        System.out.println(address.getCountry().getName());
//        user.addAddress(address);
//
//        siteUserRepository.save(user);
//    }
//
//    @Test
//    public void defaultSiteUser(){
//        SiteUser user = new SiteUser();
//        siteUserRepository.save(user);
//    }
//
//    @Test
//    public void defaultAddress(){
//        Address address = new Address();
//        addressRepository.save(address);
//    }
//
//    @Test
//    public void addAddressInExistedUser(){
////        SiteUser user = siteUserRepository.findById(9L).get();
//        SiteUser user = siteUserRepository.findSiteUserByIdAndFetch(9L);
//        Address address = addressRepository.findByIdAndFetch(11L);
////        user.addAddress(address);
//
//        siteUserRepository.save(user);
//    }
    @Test
    public void functionFindUserAndFetchAddress(){
        SiteUser user = siteUserRepository.findSiteUserByIdAndFetchAllAddress(9L);
        System.out.println(user.getAddresses().size());
        user.getAddresses().forEach((address) -> {
            System.out.println(address.getAddress().getId());
        });
    }

    @Test
    public void functionFindAddressAndFetchUser(){
        Address address = addressRepository.findByIdAndFetch(9L);
        System.out.println(address.getSiteUsers().size());
        address.getSiteUsers().forEach((siteUser) -> System.out.println(siteUser.getSiteUser().getEmailAddress()));
    }

    @Test
    public void functionAddUserIntoAddress(){
        Address address = addressRepository.findByIdAndFetch(11L);
        SiteUser siteUser = siteUserRepository.findSiteUserByIdAndFetchAllAddress(8L);

        System.out.println(address.getId());
        System.out.println(siteUser.getId());

        address.addSiteUser(siteUser, false);
        addressRepository.save(address);
    }

    @Test
    public void functionAddressIntoUser(){
        SiteUser user = siteUserRepository.findSiteUserByIdAndFetchAllAddress(9L);
        Address address = addressRepository.findByIdAndFetch(11L);
        user.addAddress(address, true);
        siteUserRepository.save(user);
    }

    @Test
    public void functionAddNewAddress(){
        Address address = new Address();
        addressRepository.save(address);
    }

    @Test
    public void functionAddNewUser(){
        SiteUser user = new SiteUser();
        siteUserRepository.save(user);
    }

    @Test
    public void functionAddNewAddressAndSiteUser(){
        SiteUser user = new SiteUser();
        System.out.println(user.getId());
        user = siteUserRepository.save(user);
        System.out.println(user.getId());
        Address address = new Address();
        System.out.println(address.getId());
        address = addressRepository.save(address);
        System.out.println(address.getId());
        address.addSiteUser(user, true);
        addressRepository.save(address);
//        siteUserRepository.save(user);

    }
    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;
    @Test
    public void functionUserService(){
        SiteUser user = new SiteUser();
        user.setId(101);
        System.out.println(user.getId());
        user = userService.saveUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void findAllAddressOfUserService(){
        Set<Address> addresses = userService.findAllAddressOfUser(9L);
        addresses.forEach(address -> {System.out.println(address.getId());});
    }

    @Test
    public void addNewAddressIntoUserService(){
        Address address = new Address();
    }

}