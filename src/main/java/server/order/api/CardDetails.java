package server.order.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jcip.annotations.Immutable;

@Immutable
public class CardDetails {
	
	final BuyType buyType;
	final String cardNumber;
	final String expDateMonth;
	final String expDateYear;
	final String CW;
	
	@JsonCreator
	public CardDetails(@JsonProperty("buyType") BuyType buyType, @JsonProperty("cardNumber") String cardNumber, @JsonProperty("expMonth") String expDateMonth, @JsonProperty("expYear") String expDateYear, @JsonProperty("cw") String CW) {
		this.buyType = buyType;
		this.cardNumber = cardNumber;
		this.expDateMonth = expDateMonth;
		this.expDateYear = expDateYear;
		this.CW = CW;
	}

	public BuyType getBuyType() {
		return buyType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getExpDateMonth() {
		return expDateMonth;
	}

	public String getExpDateYear() {
		return expDateYear;
	}

	public String getCW() {
		return CW;
	}
	
	

}
