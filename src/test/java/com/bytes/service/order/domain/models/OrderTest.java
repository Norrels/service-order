package com.bytes.service.order.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private List<OrderItem> itens;

    private Order dummyOrder;

    @Test
    void updateStatus() {
    }

    @BeforeEach
    void setup(){
        itens = List.of(
                new OrderItem("Milk Shake", "milkShake.png", BigDecimal.TEN, "Acompanhemento", "Delicioso MilkShake", 2, "M", 8L),
                new OrderItem("Suco Laranja", "sucoLaranja.png", BigDecimal.TWO, "Bebida", "Delecioso Suco", 1, "M", 8L)
        );

        dummyOrder = new Order(3L, itens);
    }

    @Test
    @DisplayName("Should set create at and update at when created a new order")
    void updateAtAndCreatedAtShouldBeSetWithLocalDateTimeNowWhenCreating(){
        Order order = new Order(13L, itens);

        assertNotNull(order.getCreatedAt());
        assertNotNull(order.getUpdatedAt());
    }

    @Test
    @DisplayName("Should be possible to make a order without a client")
    void shouldBePossibleToMakeAOrderWithoutClient(){
        assertDoesNotThrow(() -> new Order(null, itens));
    }


    @Test
    @DisplayName("Shouldn't be possible to create a order without order itens")
    void shouldNotBePossibleToCreateAOrderWithoutItens(){
        assertThrows(IllegalArgumentException.class, () -> new Order(2L, null));
    }

    @Test
    @DisplayName("Shouldn't be possible to create a order if has one invalid item")
    void shouldNotBePossibleToCreateAOrderIfHasOneInvalidItem() throws IllegalArgumentException{

        assertThrows(IllegalArgumentException.class, () -> new Order(2L, List.of(new OrderItem("Suco Laranja", "sucoLaranja.png", BigDecimal.TWO, "Bebida", "Delicioso Suco", 0, "M", 8L))));
    }

    @Test
    @DisplayName("Should be able to get order total price")
    void shouldBePossibleToGetTotal(){
        assertEquals(BigDecimal.valueOf(22), dummyOrder.getTotal());
    }

    @Test
    @DisplayName("Should be possible to update status if is already paid")
    void shouldBePossibleToUpdateStatus(){
        dummyOrder.pay();
        assertDoesNotThrow(() -> dummyOrder.updateStatus(OrderStatus.PREPARING, 2L));
    }

    @Test
    @DisplayName("It's not possible to update status to waiting paymnet")
    void itIsNotPossibleToUpdateStatusToWaitingPaymentUsingUpdateStauts(){
        assertThrows(RuntimeException.class, () -> dummyOrder.updateStatus(OrderStatus.WAITING_PAYMENT, 2L));
    }

    @Test
    @DisplayName("It's not possible to update status to canceled using update status")
    void throwExceptionIfModifyIdIsNullAndStatusIsWaitingPayment(){
        assertThrows(RuntimeException.class, () -> dummyOrder.updateStatus(OrderStatus.CANCELED, 2L));
    }

    @Test
    @DisplayName("Shouldn't be able to cancel a order if status is finished")
    void shouldNotBeAbleToCancelAOrderIfStatusIsFinished(){
        dummyOrder.pay();
        dummyOrder.updateStatus(OrderStatus.FINISHED, 2L);
        assertThrows(RuntimeException.class, () -> dummyOrder.updateStatus(OrderStatus.CANCELED, 3L));
    }

    @Test
    @DisplayName("Shouldn't be possible to cancel a order if it's already been finished")
    void shouldNotBePossibleToCancelIfItsAlreadyBeenFinished(){
        dummyOrder.pay();
        dummyOrder.updateStatus(OrderStatus.FINISHED, 3L);
        assertThrows(RuntimeException.class, () -> dummyOrder.cancel());
    }

    @Test
    @DisplayName("Should be possible to cancel a order if it's not paid yet")
    void shouldBePossibleToCancelAOrderEvenIfIsNotPaidYet(){
        assertDoesNotThrow(() -> dummyOrder.cancel());
    }

    @Test
    @DisplayName("Its not possible to change status if isn't paid yet")
    void itIsNotPossibleToUpdateStatusToWaitingPayment(){
      assertThrows(RuntimeException.class, () -> {
          dummyOrder.updateStatus(OrderStatus.PREPARING, 2L);
      });
    }

    @Test
    @DisplayName("It's not possible to pay if it's already been paid")
    void itIsNotPossibleToPayTheSameOrderIfItIsAlreadyPaid(){
        dummyOrder.pay();
        assertThrows(RuntimeException.class, () -> {
            dummyOrder.pay();
        });
    }


}