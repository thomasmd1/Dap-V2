package fr.ynov.dap.dap.services.microsoft;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MicrosoftAccountService extends MicrosoftService {
	public static String getLoginUrl(UUID state, UUID nonce) {

	    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(getAuthorizeurl());
	    urlBuilder.queryParam("client_id", getAppId());
	    urlBuilder.queryParam("redirect_uri", getRedirectUrl());
	    urlBuilder.queryParam("response_type", "code id_token");
	    urlBuilder.queryParam("scope", getScopes());
	    urlBuilder.queryParam("state", state);
	    urlBuilder.queryParam("nonce", nonce);
	    urlBuilder.queryParam("response_mode", "form_post");

	    return urlBuilder.toUriString();
	  }
}
