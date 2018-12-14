package server.security.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import server.security.api.User;

public class UserInMemoryRepository {
	
	private volatile HashMap<String, User> userRepo = new HashMap<>(); 
	
	public UserInMemoryRepository() {
		// TODO Auto-generated constructor stub
		populateMap();
	}
	
	public Optional<User> getUser(String login) {
		return Optional.ofNullable(userRepo.get(login));
	}
	
	private void populateMap() {
		User bugaj = new User("Bugaj", BCrypt.hashpw("dx", BCrypt.gensalt(4)));
		bugaj.addPermissions(Arrays.asList("admin", "user"));
		
		User raberr = new User("Raberr", BCrypt.hashpw("xxx", BCrypt.gensalt(4)));
		raberr.addPermissions(Arrays.asList("admin", "user"));
		
		this.userRepo.put(bugaj.getLogin(), bugaj);
		this.userRepo.put(raberr.getLogin(), raberr);
	}

}
