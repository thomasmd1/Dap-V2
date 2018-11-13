package fr.ynov.dap.dap.data.google;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AppUser {

	@Id
	@GeneratedValue
	Integer id;

	String userKey;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="owner")
	List<GoogleAccount> googleAccounts;

	public AppUser() {
		super();
		googleAccounts = new ArrayList<GoogleAccount>();
	}

	public void addGoogleAccount(GoogleAccount account){
	    account.setOwner(this);
	    this.getAccounts().add(account);
	}
	
	public List<GoogleAccount> getAccounts(){
		return googleAccounts;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
}
