package server.order.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.jcip.annotations.Immutable;

@Immutable
public class Address {
	final String address1;
	final String address2;
	final String address3;
	final String city;
	final String postcode;
	final Country country;
	
	@JsonCreator
	public Address(@JsonProperty("address1") String address1,@JsonProperty("address2") String address2,@JsonProperty("address3") String address3,
			@JsonProperty("city") String city, @JsonProperty("postcode") String postcode, @JsonProperty("country") Country country) {
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
	}
	
	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getAddress3() {
		return address3;
	}

	public String getCity() {
		return city;
	}

	public String getPostcode() {
		return postcode;
	}

	public Country getCountry() {
		return country;
	}

}
