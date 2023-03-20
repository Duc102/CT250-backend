package ecommerce.controller;

import ecommerce.models.SiteUser;
import ecommerce.services.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public boolean login(@RequestBody SiteUser siteUser){
        return siteUserService.login(siteUser.getEmailAddress(), siteUser.getPassword());
    }
}
