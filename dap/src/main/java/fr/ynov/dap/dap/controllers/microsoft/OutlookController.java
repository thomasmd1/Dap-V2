package fr.ynov.dap.dap.controllers.microsoft;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.services.microsoft.MicrosoftService;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.interfaces.OutlookService;
import fr.ynov.dap.dap.services.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.dap.data.microsoft.PagedResult;

@Controller
public class OutlookController {

  @RequestMapping("/mail")
  public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    HttpSession session = request.getSession();
    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      return "Please sign in to continue.";
    }

    String tenantId = (String)session.getAttribute("userTenantId");

    tokens = MicrosoftService.ensureTokens(tokens, tenantId);

    String email = (String)session.getAttribute("userEmail");

    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

    // Retrieve messages from the inbox
    String folder = "inbox";
    // Sort by time received in descending order
    String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
    // Return at most 10 messages
    Integer maxResults = 10;
    Integer nbUnreadEmails = 0;

    try {
      PagedResult<Message> messages = outlookService.getMessages(
          folder, sort, properties, maxResults)
          .execute().body();
      model.addAttribute("messages", messages.getValue());
      
      Message[] messageList = messages.getValue();
      
      for(int i = 0; i < messageList.length; i++) {
    	  if(!messageList[i].getIsRead()) {
    		  nbUnreadEmails++;
    	  }
      }
      model.addAttribute("nbUnreadEmails", nbUnreadEmails);
      
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      Log.info("erreur" + e);
    }

    return "mail";
  }
}
