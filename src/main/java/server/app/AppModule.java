package server.app;

public class AppModule {
	
	public AppModule() {
		
	}
	
	public AppService createAppService() {
		return new AppService();
	}

}
