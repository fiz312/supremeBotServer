package server.security;

import server.security.repository.UserInMemoryRepository;

public class SecurityModule {
	
	private final UserInMemoryRepository userInMemoryRepository;
	
	public SecurityModule() {
		this(new UserInMemoryRepository());
	}

	public SecurityModule(UserInMemoryRepository userInMemoryRepository) {
		this.userInMemoryRepository = userInMemoryRepository;
	}
	
	public SecurityService createSecurityService() {
		return new SecurityService(userInMemoryRepository);
	}

}
