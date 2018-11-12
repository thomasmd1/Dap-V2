package fr.ynov.dap.dap.controllers.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.google.ContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
   
    /**
     * Gets the nb contact.
     *
     * @param user the user
     * @return the nb contact
     */
	@Autowired
	private ContactService contactService;

	final static Logger LOG = LogManager.getLogger(CalendarController.class);

    @RequestMapping("nbCont/{userKey}")
    public @ResponseBody String getNbContact(@PathVariable String userKey) {
       
        Integer nbContact = null;
        String response = "errorOccurs";
   
        try {
        	nbContact = contactService.getNbContact(userKey);
        	response = nbContact.toString();
           
        } catch (IOException e) {
        	 LOG.error("Erreur Contact", e);
             
        } catch (GeneralSecurityException e) {
        	 LOG.error("Erreur Contact", e);
        }
        return response;
    }
}