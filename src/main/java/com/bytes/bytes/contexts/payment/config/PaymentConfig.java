package com.bytes.bytes.contexts.payment.config;

import com.bytes.bytes.contexts.payment.application.PaymentService;
import com.bytes.bytes.contexts.payment.application.useCases.CreatePaymentUseCase;
import com.bytes.bytes.contexts.payment.application.useCases.FindPaymentByOrderIdUseCase;
import com.bytes.bytes.contexts.payment.domain.port.oubound.PaymentRepositoryPort;
import com.bytes.bytes.contexts.shared.useCases.PayOrderUseCasePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    @Bean
    public CreatePaymentUseCase createPaymentUseCase(PaymentRepositoryPort paymentRepositoryPort){
        return new CreatePaymentUseCase(paymentRepositoryPort);
    }
    @Bean
    public FindPaymentByOrderIdUseCase findPaymentByOrderIdUseCase(PaymentRepositoryPort paymentRepositoryPort){
        return new FindPaymentByOrderIdUseCase(paymentRepositoryPort);
    }

    @Bean
    public PaymentService paymentService(CreatePaymentUseCase createPaymentUseCase, FindPaymentByOrderIdUseCase findPaymentByOrderIdUseCase, PayOrderUseCasePort payOrderUseCasePort) {
        return new PaymentService(createPaymentUseCase, findPaymentByOrderIdUseCase, payOrderUseCasePort);
    }
}
