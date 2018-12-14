package server.order.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jcip.annotations.Immutable;

@Immutable
public class ClientDetails {
	
	final String name;
	final String email;
	final String telephoneNumber;
	
	@JsonCreator
	public ClientDetails(@JsonProperty("name") String name,@JsonProperty("email") String email,@JsonProperty("telephoneNumber") String telephoneNumber) {
		this.name = name;
		this.email = email;
		this.telephoneNumber = telephoneNumber;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	

}
