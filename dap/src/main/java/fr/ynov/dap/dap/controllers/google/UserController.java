package fr.ynov.dap.dap.controllers.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	public AppUserRepository appUserRepo;

	static final Logger LOG = LogManager.getLogger(UserController.class);

	@RequestMapping("/add/{userKey}")
    public String addAccount(@PathVariable final String userKey){
		AppUser appUser = new AppUser();
		appUser.setUserKey(userKey);
		appUserRepo.save(appUser);

		return appUserRepo.findByUserKey(userKey).getUserKey();  
    }
}
