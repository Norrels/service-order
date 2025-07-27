package com.bytes.service.order.contexts.order.config;

import com.bytes.service.order.contexts.order.application.OrderService;
import com.bytes.bytes.contexts.order.application.useCases.*;
import com.bytes.service.order.contexts.order.application.useCases.*;
import com.bytes.service.order.contexts.order.domain.ports.outbound.OrderRepositoryPort;
import com.bytes.service.order.contexts.order.mappers.OrderMappper;
import com.bytes.service.order.contexts.shared.useCases.CustomerExistsUseCasePort;
import com.bytes.service.order.contexts.shared.useCases.FindProductByIdUseCasePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort orderRepositoryPort, CustomerExistsUseCasePort findUserByIdUseCase, FindProductByIdUseCasePort findProductByIdUseCase) {
        return new CreateOrderUseCase(orderRepositoryPort, findUserByIdUseCase, findProductByIdUseCase);
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
    public PayOrderUseCase payOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new PayOrderUseCase(orderRepositoryPort);
    }

    @Bean
    public GetOrderDTOByIdUseCase getOrderDTOByIdUseCase(OrderRepositoryPort orderRepositoryPort, OrderMappper orderMappper) {
        return new GetOrderDTOByIdUseCase(orderRepositoryPort, orderMappper);
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