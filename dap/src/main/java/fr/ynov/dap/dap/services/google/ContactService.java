package fr.ynov.dap.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Service;

/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleService {

	/**
	 * Instantiates a new contact service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ContactService() throws IOException, GeneralSecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the nb contact.
	 *
	 * @param user the user
	 * @return the nb contact
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public int getNbContact(String user) throws IOException, GeneralSecurityException {
		String userEmail = user == null ? getDefaultUser() : user;
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT,userEmail))
                .setApplicationName(getCfg().getApplicationName())
                .build();

      
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(2000)
                .setPersonFields("names,emailAddresses")
                .execute();
        
        return response.getTotalItems();
    }
}
