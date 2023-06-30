package com.asif.kafka_microservice.orderservice.controller;

import com.asif.kafka_microservice.basedomains.dto.Order;
import com.asif.kafka_microservice.basedomains.dto.OrderEvent;
import com.asif.kafka_microservice.orderservice.Kafka.OrderProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {


    private OrderProducer orderProducer;


    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();

        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order stats is in pending state..!!");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return new ResponseEntity<>("Order Placed Successfully..!!", HttpStatus.CREATED);
    }
}
