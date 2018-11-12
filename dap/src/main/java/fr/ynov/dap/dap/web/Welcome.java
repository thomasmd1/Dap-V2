package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.services.google.GmailService;

@Controller
public class Welcome {
	@Autowired
	private GmailService gmailService;

	@RequestMapping("/welcome")
	public String welcome(ModelMap model,@RequestParam(value="userKey", required=true) String userKey){
		Log.warn(userKey);
		try {
			Integer nbunreadEmails = gmailService.nbrEmailUnread(userKey);
			model.addAttribute("nbEmails",nbunreadEmails);

			DataStore<StoredCredential> i;
			i = gmailService.getFlow().getCredentialDataStore();
			HashMap<String,String> hashMap = new HashMap<>();

			for (String userEmail: i.keySet()) {
				hashMap.put(userEmail, i.get(userEmail).getAccessToken());
			}
			model.addAttribute("credentials", hashMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "welcome";
	}
}
