package server.order;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.codehaus.groovy.ant.Groovy;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.http.client.direct.HeaderClient;

import com.google.common.collect.Maps;

import groovy.test.GroovyAssert;
import ratpack.exec.Promise;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;
import ratpack.pac4j.RatpackPac4j;
import server.order.api.Order;
import server.order.repository.OrderInMemoryRepository;
import server.order.repository.OrderRepositoryProcessor;

public class OrderService {

	private final OrderRepositoryProcessor orderInMemoryProcessor;

	public OrderService(OrderInMemoryRepository orderInMemoryRepository) {
		this.orderInMemoryProcessor = new OrderRepositoryProcessor(orderInMemoryRepository);
	}

	public Action<Chain> orderApi() {
		return chain -> chain.prefix("orders", orders());
	}

	private Action<Chain> orders() {
		return chain -> {
			chain.path("order", ctx -> ctx.byMethod(e -> 
				e.get(getOrder())
				.post(modifyOrder())));
		};
	}

	private Handler getOrder() {
		return ctx -> {
			CommonProfile user = (CommonProfile) ctx.get(UserProfile.class);
			String login = user.getId();
			//System.out.println(orderInMemoryProcessor.getOrder(login).thenApply(x -> System.out.println(x.get().)));
			ctx.render(Promise.async(downstream -> downstream
					.accept(orderInMemoryProcessor.getOrder(login).thenApply(x -> x.get()).thenApply(Jackson::json))));
		};
	}

	private Handler modifyOrder() {
		return ctx -> ctx.parse(Jackson.fromJson(Order.class)).then(order -> {
			CommonProfile user = (CommonProfile) ctx.get(UserProfile.class);
			String login = user.getId();
			ctx.render(Promise.async(downstream -> downstream
					.accept(orderInMemoryProcessor.modifyOrder(login, order).thenApply(Jackson::json))));
		});
	}
	
	public OrderRepositoryProcessor getRepositoryProcessor() {
		return this.orderInMemoryProcessor;
	}

}
