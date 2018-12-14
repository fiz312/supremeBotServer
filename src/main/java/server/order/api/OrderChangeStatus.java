package server.order.api;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

import net.jcip.annotations.Immutable;

@Immutable
public class OrderChangeStatus {
	public final boolean ok;
	
	public final Optional<String> problem;
	
	@JsonCreator
	public OrderChangeStatus(Optional<String> problem) {
		this.problem = problem;
		this.ok = !problem.isPresent();
	}
	

}
