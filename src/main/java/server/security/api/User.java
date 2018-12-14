package server.security.api;

import org.pac4j.core.profile.CommonProfile;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import net.jcip.annotations.Immutable;

@Immutable
public class User extends CommonProfile {
	
	final String login;

	final String password;

	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
}
