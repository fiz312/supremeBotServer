package server.order.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.order.api.Order;
import server.order.api.OrderChangeStatus;
import server.security.api.User;

public class OrderRepositoryProcessor {
	
	private final Executor repoExecutor = Executors.newSingleThreadExecutor();
	
	private final OrderInMemoryRepository orderInMemoryRepository;
	
	public OrderRepositoryProcessor(OrderInMemoryRepository orderInMemoryRepository) {
		this.orderInMemoryRepository = orderInMemoryRepository;
	}
	
	public CompletionStage<OrderChangeStatus> modifyOrder(final String login, final Order order) {
		final CompletableFuture<OrderChangeStatus> result = new CompletableFuture<>();
		repoExecutor.execute(() -> {
			result.complete(this.orderInMemoryRepository.modifyOrder(login, order));
		}); 
		return result;
	}
	
	public CompletionStage<Optional<Order>> getOrder(final String login) {
		System.out.println(Thread.currentThread().getName());
		return CompletableFuture.completedFuture(orderInMemoryRepository.getOrder(login));
	}

}
