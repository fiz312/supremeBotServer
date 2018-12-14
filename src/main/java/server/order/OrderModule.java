package server.order;

import server.order.repository.OrderInMemoryRepository;

public class OrderModule {
	
	private final OrderInMemoryRepository orderInMemoryRepository;
	
	public OrderModule() {
		this(new OrderInMemoryRepository());
	}
	
	public OrderModule(OrderInMemoryRepository orderInMemoryRepository) {
		this.orderInMemoryRepository = orderInMemoryRepository;
	}
	
	public OrderService createOrderService() {
		return new OrderService(orderInMemoryRepository);
	}

}
