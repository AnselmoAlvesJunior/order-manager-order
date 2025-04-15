package com.order_manager.order.service;

import com.order_manager.order.model.Order;
import com.order_manager.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        String messageJson = String.format("{\"orderId\":\"%s\", \"status\":\"%s\"}",
                savedOrder.getId().toString(),
                savedOrder.getStatus());
        kafkaTemplate.send("order-payment", messageJson);
        return savedOrder;
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        String messageJson = String.format("{\"orderId\":\"%s\", \"status\":\"%s\"}",
                order.getId().toString(),
                order.getStatus());
        kafkaTemplate.send("order-payment", messageJson);
        System.out.println(messageJson);
        return orderRepository.save(order);
    }
}