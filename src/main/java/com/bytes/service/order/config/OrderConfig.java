package com.bytes.service.order.config;

import com.bytes.service.order.application.OrderService;
import com.bytes.service.order.application.useCases.GetOrdersByStatusUseCase;
import com.bytes.service.order.application.useCases.order.*;
import com.bytes.service.order.domain.outbound.ProductRepositoryPort;
import com.bytes.service.order.domain.ports.outbound.CustomerRepositoryPort;
import com.bytes.service.order.domain.ports.outbound.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort orderRepositoryPort, CustomerRepositoryPort customerRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        return new CreateOrderUseCase(orderRepositoryPort, customerRepositoryPort, productRepositoryPort);
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new GetOrderByIdUseCase(orderRepositoryPort);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new CancelOrderUseCase(orderRepositoryPort);
    }

    @Bean
    public UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new UpdateOrderStatusUseCase(orderRepositoryPort);
    }

    @Bean
    public GetOrdersByStatusUseCase getOrdersByStatusUseCase(OrderRepositoryPort orderRepositoryPort){
        return new GetOrdersByStatusUseCase(orderRepositoryPort);
    }

    @Bean
    public OrderService orderService(CreateOrderUseCase createOrderUseCase, GetOrderByIdUseCase getOrderByIdUseCase, CancelOrderUseCase cancelOrderUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, GetOrdersByStatusUseCase getOrdersByStatusUseCase) {
        return new OrderService(createOrderUseCase, getOrderByIdUseCase, cancelOrderUseCase, updateOrderStatusUseCase, getOrdersByStatusUseCase);
    }
}