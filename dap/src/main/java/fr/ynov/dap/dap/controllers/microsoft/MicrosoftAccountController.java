package fr.ynov.dap.dap.controllers.microsoft;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.interfaces.OutlookService;
import fr.ynov.dap.dap.data.microsoft.IdToken;
import fr.ynov.dap.dap.data.microsoft.OutlookUser;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.services.microsoft.MicrosoftService;
import fr.ynov.dap.dap.services.microsoft.OutlookServiceBuilder;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftAccountController {
	
	final static Logger LOG = LogManager.getLogger(MicrosoftAccountController.class);
	
	@RequestMapping("/add/account/{accountName}")
	public String addAccount(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
	  UUID state = UUID.randomUUID();
	  UUID nonce = UUID.randomUUID();

	  // Save the state and nonce in the session so we can
	  // verify after the auth process redirects back
	  HttpSession session = request.getSession();
	  session.setAttribute("expected_state", state);
	  session.setAttribute("expected_nonce", nonce);

	  String loginUrl = MicrosoftService.getLoginUrl(state, nonce);
	  //model.addAttribute("loginUrl", loginUrl);
	  // Name of a definition in WEB-INF/defs/pages.xml
	  
	  response.sendRedirect(loginUrl);
	  
	  return "loginSuccess";
	}
	
	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	  public String authorize(
	      @RequestParam("code") String code, 
	      @RequestParam("id_token") String idToken,
	      @RequestParam("state") UUID state,
	      HttpServletRequest request, Model model) { {
	    // Get the expected state value from the session
	    HttpSession session = request.getSession();
	    UUID expectedState = (UUID) session.getAttribute("expected_state");
	    UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

	    // Make sure that the state query parameter returned matches
	    // the expected state
	    if (state.equals(expectedState)) {
	    	IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
	    	if (idTokenObj != null) {
	    	  TokenResponse tokenResponse = MicrosoftService.getTokenFromAuthCode(code, idTokenObj.getTenantId());
	    	  session.setAttribute("tokens", tokenResponse);
	    	  session.setAttribute("userConnected", true);
	    	  session.setAttribute("userName", idTokenObj.getName());
	    	  
	    	// Get user info
	    	  OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
	    	  OutlookUser user;
	    	  try {
	    	    user = outlookService.getCurrentUser().execute().body();
	    	    session.setAttribute("userEmail", user.getMail());
	    	  } catch (IOException e) {
	    	    session.setAttribute("error", e.getMessage());
	    	  }
	    	  
	    	  session.setAttribute("userTenantId", idTokenObj.getTenantId());
	    	} else {
	    	  session.setAttribute("error", "ID token failed validation.");
	    	  LOG.info("erreur 1");
	    	}
	    }
	    else {
	      session.setAttribute("error", "Unexpected state returned from authority.");
	      LOG.info("erreur 2");
	    }
	     
	    model.addAttribute("userName", session.getAttribute("userName"));
	    model.addAttribute("tokens", session.getAttribute("tokens"));
	    
	    return "loginSuccess";
	  }
	}
	
	  @RequestMapping("/logout")
	  public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    session.invalidate();
	    return "logout Success";
	  }
}
