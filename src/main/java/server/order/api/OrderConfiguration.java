package server.order.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jcip.annotations.Immutable;

@Immutable
public class OrderConfiguration {
	
	final ClientDetails clientDetails;
	final Address address;
	final CardDetails cardDetails;
	
	@JsonCreator
	public OrderConfiguration(@JsonProperty("clientDetails") ClientDetails clientDetails, @JsonProperty("address")Address address, @JsonProperty("cardDetails") CardDetails cardDetails) {
		this.clientDetails = clientDetails;
		this.address = address;
		this.cardDetails = cardDetails;
	}

	public ClientDetails getClientDetails() {
		return clientDetails;
	}

	public Address getAddress() {
		return address;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

}
