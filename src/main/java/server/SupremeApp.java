package server;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.app.AppModule;
import server.app.AppService;
import server.order.OrderModule;
import server.order.OrderService;
import server.security.SecurityModule;
import server.security.SecurityService;

public class SupremeApp {
	public static void main(String args[]) throws Exception {
		SecurityModule securityModule = new SecurityModule();
		OrderModule orderModule = new OrderModule(); 
		AppModule appModule = new AppModule();
		
		SecurityService securityService = securityModule.createSecurityService();
		OrderService orderService = orderModule.createOrderService();
		AppService appService = appModule.createAppService();
		
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> {
			try {
				Server server = new Server(securityService, orderService, appService);
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		//System.setProperty("webdriver.gecko.driver", new File("C:\\Users\\Raberr\\Desktop\\dev\\geckodriver.exe").getAbsolutePath());
		SupremeBot supremeBot = new SupremeBot(orderService);
		//supremeBot.run();
	}
}
