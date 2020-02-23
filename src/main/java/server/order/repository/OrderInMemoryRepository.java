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
		Address address = new Address("1 Kasz", "pie", "Pol", "K", "2", Country.POLAND);
		ClientDetails clientDetails = new ClientDetails("Ka", "mustan", "0000");
		CardDetails cardDetails = new CardDetails(BuyType.PAYPAL, "23434534534", "2", "14", "99");
		OrderConfiguration orderConfiguration = new OrderConfiguration(clientDetails, address, cardDetails);
		Item item = new Item("GO-Panel", "https://asGKPEPU-A.jpg" , "hats");
		Order order = new Order(orderConfiguration, false, item);
		orderMap.put("rr", order);
	}

	public OrderChangeStatus modifyOrder(final String login, final Order order) {
		orderMap.put(login, order);
		return new OrderChangeStatus(Optional.empty());
	}
	
	public Optional<Order> getOrder(final String login) {
		return Optional.of(orderMap.get(login));
	}

}
