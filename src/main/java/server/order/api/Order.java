package server.order.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jcip.annotations.Immutable;

@Immutable
public class Order {
	
	final OrderConfiguration orderConfiguration;
	final boolean execute;
	final Item item;
	
	@JsonCreator
	public Order(@JsonProperty("orderConfiguration") OrderConfiguration orderConfiguration,@JsonProperty("execute") boolean execute, @JsonProperty("item") Item item) {
		this.orderConfiguration = orderConfiguration;
		this.execute = execute;
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public OrderConfiguration getOrderConfiguration() {
		return orderConfiguration;
	}

	public boolean isExecute() {
		return execute;
	}
	
	
}
