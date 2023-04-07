package ecommerce.services;

import ecommerce.models.*;
import ecommerce.repository.AddressRepository;
import ecommerce.repository.ShoppingCartRepository;
import ecommerce.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SiteUserService {
    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private AddressRepository addressRepository;

    public boolean register(SiteUser siteUser){
        SiteUser existed = siteUserRepository.findSiteUserByEmailAddress(siteUser.getEmailAddress());
        if(existed == null){
            ShoppingCart shoppingCart = new ShoppingCart();
            SiteUser newSiteUser = siteUserRepository.save(siteUser);
            shoppingCart.setSiteUser(newSiteUser);
            shoppingCartRepository.save(shoppingCart);
            return true;
        }
        else return false;
    }

    public SiteUser login(String email, String password){
        SiteUser siteUser = siteUserRepository.findSiteUserByEmailAddressAndPassword(email, password);
        if(siteUser != null)
            return siteUser;
        return null;
    }

    public SiteUser updateInfo(SiteUser updatedSiteUser){
        SiteUser siteUser = siteUserRepository.findById(updatedSiteUser.getId()).orElse(null);
        siteUser.setName(updatedSiteUser.getName());
        siteUser.setEmailAddress(updatedSiteUser.getEmailAddress());
        siteUser.setPhoneNumber(updatedSiteUser.getPhoneNumber());
        Set<UserAddressAssociation> addresses = siteUser.getAddresses();
//      =====================
//        Payment method default
//      =====================
        updatedSiteUser.getAddresses().forEach(a ->{
            if(a.getAddress().getId() == 0L){
                Address newAddress = new Address();
                newAddress.setFirstAddressLine(a.getAddress().getFirstAddressLine());
                newAddress.setSecondAddressLine(a.getAddress().getSecondAddressLine());
                newAddress.setCity(a.getAddress().getCity());
                newAddress.setCountry(a.getAddress().getCountry());
                newAddress = addressRepository.save(newAddress);
                UserAddressAssociation newAss = new UserAddressAssociation();
                newAss.getId().setUserId(siteUser.getId());
                newAss.getId().setAddressId(newAddress.getId());
                newAss.setDefault(a.isDefault());
                newAss.setSiteUser(siteUser);
                addresses.add(newAss);
            } else {
                for (UserAddressAssociation address : addresses) {
                    if(address.getAddress().getId() == a.getAddress().getId()){
                        address.getAddress().setFirstAddressLine(a.getAddress().getFirstAddressLine());
                        address.getAddress().setSecondAddressLine(a.getAddress().getSecondAddressLine());
                        address.getAddress().setCity(a.getAddress().getCity());
                        break;
                    }
                }
            }
        });
        return siteUserRepository.save(siteUser);
    }

    public int countSiteUser(){
        return siteUserRepository.countSiteUser();
    }

    public List<SiteUser> getAllSiteUsers(){
        return siteUserRepository.findAll();
    }

    public List<SiteUser> getSiteUsersByName(String name){
        return siteUserRepository.findSiteUsersByName(name);
    }

    public SiteUser getSiteUserById(Long id){
        return siteUserRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id){
        siteUserRepository.deleteById(id);
    }


}
