package fr.ynov.dap.dap.controllers.microsoft;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.dap.services.microsoft.MicrosoftService;

@Controller
public class IndexController {
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
	  UUID state = UUID.randomUUID();
	  UUID nonce = UUID.randomUUID();

	  // Save the state and nonce in the session so we can
	  // verify after the auth process redirects back
	  HttpSession session = request.getSession();
	  session.setAttribute("expected_state", state);
	  session.setAttribute("expected_nonce", nonce);

	  String loginUrl = MicrosoftService.getLoginUrl(state, nonce);
	  model.addAttribute("loginUrl", loginUrl);
	  // Name of a definition in WEB-INF/defs/pages.xml
	  
	  response.sendRedirect(loginUrl);
	  return "index";
	}
}
