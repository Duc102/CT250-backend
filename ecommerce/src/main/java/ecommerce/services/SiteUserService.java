package ecommerce.services;

import ecommerce.models.SiteUser;
import ecommerce.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
