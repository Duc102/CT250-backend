package ecommerce.controller;

import ecommerce.models.SiteUser;
import ecommerce.services.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/siteUser")
@CrossOrigin(origins = "http://localhost:3000")
public class SiteUserController {
    @Autowired
    private SiteUserService siteUserService;

    @PostMapping("/register")
    public boolean register(@RequestBody SiteUser siteUser){
        return siteUserService.register(siteUser);

    }

    @PostMapping("/login")
    public SiteUser login(@RequestBody SiteUser siteUser){
        return siteUserService.login(siteUser.getEmailAddress(), siteUser.getPassword());
    }

    @GetMapping("/countSiteUser")
    public int countSiteUser(){
        return siteUserService.countSiteUser();
    }

    @GetMapping("/allSiteUsers")
    public List<SiteUser> getAllSiteUsers(){
        return siteUserService.getAllSiteUsers();
    }

    @GetMapping("/getSiteUsersByName/{name}")
    public List<SiteUser> getSiteUsersByName(@PathVariable String name){
        return siteUserService.getSiteUsersByName(name);
    }

    @GetMapping("/getSiteUserById/{id}")
    public SiteUser getSiteUserById(@PathVariable Long id){
        return siteUserService.getSiteUserById(id);
    }

    @DeleteMapping("/deleteSiteUser/{id}")
    public boolean deleteSite(@PathVariable Long id){
        siteUserService.deleteUserById(id);
        return true;
    }

}
