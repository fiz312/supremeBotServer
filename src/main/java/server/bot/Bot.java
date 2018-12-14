package server.bot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.collect.Lists;

import server.order.api.Address;
import server.order.api.CardDetails;
import server.order.api.ClientDetails;
import server.order.api.Item;
import server.order.api.Order;

public class Bot {
	private final Address address;
	private final ClientDetails clientDetails;
	private final CardDetails cardDetails;
	private final ItemExtractor itemExtractor;
	private final Item item;
	private final FirefoxDriver firefoxDriver;

	public Bot(Order order, FirefoxDriver firefoxDriver) {
		this.item = order.getItem();
		this.clientDetails = order.getOrderConfiguration().getClientDetails();
		this.cardDetails = order.getOrderConfiguration().getCardDetails();
		this.address = order.getOrderConfiguration().getAddress();
		this.itemExtractor = new ItemExtractor();
		this.firefoxDriver = firefoxDriver;
	}

	public void start() {
		long last = System.currentTimeMillis();
		itemExtractor.prepareBaseItem(item.getImage());
		List<Element> items = checkForItem();
		long current = System.currentTimeMillis();
		System.out.println(current-last);
		String href = itemExtractor.extractItem(items);
		firefoxDriver.navigate().to("https://www.supremenewyork.com/" + href);
		System.out.println(href);
	}

	public void waitForDrop() {
		while ((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
				|| (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) != 23)) {
			try {
				System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
	}

	public List<Element> checkForItem() {
		Connection con = Jsoup.connect("https://www.supremenewyork.com/shop/all/" + item.getCategory());
		Document doc;
		try {
			List<Element> items;
			do {
				Thread.sleep(10);
				doc = con.get();
				items = doc.getElementsByClass("inner-article").stream().map(e -> e)
						.filter(e -> item.getName().equals(e.child(1).text())).collect(Collectors.toList());
			} while (items.isEmpty());
			return items;
		} catch (IOException | InterruptedException e) {
			return Lists.newArrayList();
		}
	}
}
