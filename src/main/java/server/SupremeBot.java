package server;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import server.bot.Bot;
import server.order.OrderService;
import server.order.api.Order;
import server.order.repository.OrderRepositoryProcessor;

class SupremeBot {

	private final OrderRepositoryProcessor orderRepositoryProcessor;
	
	SupremeBot(OrderService orderService) {
		this.orderRepositoryProcessor = orderService.getRepositoryProcessor();
	}
	
	public void run() throws InterruptedException, ExecutionException {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile(new File(
				"C:\\Users\\Raberr\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\3iuadhtk.dev-edition-default-1523639938203"));
		profile.setPreference("dom.webdriver.enabled", false);
		profile.setPreference("permissions.default.image", 2);
		firefoxOptions.setProfile(profile);
		FirefoxDriver webDriver = new FirefoxDriver(firefoxOptions);
		webDriver.executeScript("navigator.webdriver = false;", 1);
		
		Future<Optional<Order>> order = (Future<Optional<Order>>) orderRepositoryProcessor.getOrder("Raberr");
		Bot bot = new Bot(order.get().get(), webDriver);
		bot.waitForDrop();
		long last = System.currentTimeMillis();
		bot.start();
		long current = System.currentTimeMillis();
		System.out.println(current-last);
	}
}
