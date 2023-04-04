package ecommerce.services;

import ecommerce.models.SiteUser;
import ecommerce.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteUserService {
    @Autowired
    private SiteUserRepository siteUserRepository;

    public boolean register(SiteUser siteUser){
        SiteUser existed = siteUserRepository.findSiteUserByEmailAddress(siteUser.getEmailAddress());
        if(existed == null)
            return true;
        else return false;
    }

    public SiteUser login(String email, String password){
        SiteUser siteUser = siteUserRepository.findSiteUserByEmailAddressAndPassword(email, password);
        if(siteUser != null)
            return siteUser;
        return null;
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
