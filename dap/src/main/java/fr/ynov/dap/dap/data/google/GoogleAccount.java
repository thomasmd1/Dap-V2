package fr.ynov.dap.dap.data.google;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {

	@Id
	@GeneratedValue
	Integer id;

	@ManyToOne
	AppUser owner;
	
	String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(AppUser appUser) {
		// TODO Auto-generated method stub
		this.owner = appUser;
	}

	public AppUser getOwner() {
		return owner;
	}
}
