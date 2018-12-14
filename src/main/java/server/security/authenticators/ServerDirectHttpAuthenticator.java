package server.security.authenticators;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;

import server.security.api.User;
import server.security.repository.UserInMemoryRepository;

public class ServerDirectHttpAuthenticator implements Authenticator<UsernamePasswordCredentials> {
	
	private UserInMemoryRepository userRepository;
	
	public ServerDirectHttpAuthenticator(UserInMemoryRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void validate(final UsernamePasswordCredentials credentials, WebContext context) {
		   if (credentials == null) {
	            throw new CredentialsException("No credential");
	        }
	        String username = credentials.getUsername();
	        String password = credentials.getPassword();
	        if (CommonHelper.isBlank(username)) {
	            throw new CredentialsException("Username cannot be blank");
	        }
	        if (CommonHelper.isBlank(password)) {
	            throw new CredentialsException("Password cannot be blank");
	        }
	        Optional<User> tempUser = userRepository.getUser(username);
	        if(!tempUser.isPresent()) {
	        	throw new CredentialsException("User with that username doesn't exist");
	        }
	        if (!BCrypt.checkpw(password, tempUser.get().getPassword())) {
	            throw new CredentialsException("Password is incorrect");
	        }
	        final CommonProfile profile = new CommonProfile();
	        profile.addPermissions(tempUser.flatMap(x -> Optional.of(x.getPermissions())).get());
	        profile.addRoles(tempUser.flatMap(x -> Optional.of(x.getRoles())).get());
	        profile.setId(tempUser.flatMap(x -> Optional.of(x.getLogin())).get());
	        credentials.setUserProfile(profile);
	}

}
