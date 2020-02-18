package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.repository.OrderRepository;

@Service
public class AdminOrderService {
    private OrderRepository orderRepository;

    @Autowired
    public AdminOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> getOrdersByStatement(OrderStatement statement, Pageable pageable) {
        return orderRepository
                .findAllByOrderStatementOrderByDate(
                        statement, pageable
                );
    }
}
