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

import fr.ynov.dap.dap.data.interfaces.OutlookService;
import fr.ynov.dap.dap.data.microsoft.Event;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.services.microsoft.MicrosoftService;
import fr.ynov.dap.dap.services.microsoft.OutlookServiceBuilder;



@Controller
public class EventMicrosoftController {

  @RequestMapping("/microsoftEvents")
  public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    HttpSession session = request.getSession();
    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      return "redirect:/index.html";
    }

    String tenantId = (String)session.getAttribute("userTenantId");

    tokens = MicrosoftService.ensureTokens(tokens, tenantId);

    String email = (String)session.getAttribute("userEmail");

    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

    // Sort by start time in descending order
    String sort = "start/dateTime DESC";
    // Only return the properties we care about
    String properties = "organizer,subject,start,end";
    // Return at most 10 events
    Integer maxResults = 10;

    try {
      PagedResult<Event> events = outlookService.getEvents(
          sort, properties, maxResults)
          .execute().body();
      model.addAttribute("events", events.getValue());
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      Log.info("erreur" + e);
    }

    return "event";
  }
}
