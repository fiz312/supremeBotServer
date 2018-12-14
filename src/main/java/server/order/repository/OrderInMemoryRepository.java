package server.order.repository;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import server.order.api.Address;
import server.order.api.BuyType;
import server.order.api.CardDetails;
import server.order.api.ClientDetails;
import server.order.api.Country;
import server.order.api.Item;
import server.order.api.Order;
import server.order.api.OrderChangeStatus;
import server.order.api.OrderConfiguration;

public class OrderInMemoryRepository {
	
	private volatile ConcurrentHashMap<String, Order> orderMap;
	
	public OrderInMemoryRepository() {
		this.orderMap = new ConcurrentHashMap<>();
		populateMap();
	}
	
	private void populateMap() {
		Address address = new Address("19 Kasztanowa", "piekoszyn", "Poland", "Kielce", "26-000", Country.POLAND);
		ClientDetails clientDetails = new ClientDetails("Kac Rob", "mustang19@deb.pl", "000000000");
		CardDetails cardDetails = new CardDetails(BuyType.PAYPAL, "23425432534534534", "12", "2014", "999");
		OrderConfiguration orderConfiguration = new OrderConfiguration(clientDetails, address, cardDetails);
		Item item = new Item("GORE-TEX 6-Panel", "https://assets.supremenewyork.com/158616/ma/7vgGKPEPU-A.jpg" , "hats");
		Order order = new Order(orderConfiguration, false, item);
		orderMap.put("Raberr", order);
	}

	public OrderChangeStatus modifyOrder(final String login, final Order order) {
		orderMap.put(login, order);
		return new OrderChangeStatus(Optional.empty());
	}
	
	public Optional<Order> getOrder(final String login) {
		return Optional.of(orderMap.get(login));
	}

}
