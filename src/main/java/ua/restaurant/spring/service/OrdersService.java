package ua.restaurant.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.dto.OrdersDTO;
import ua.restaurant.spring.repository.OrderRepository;

import java.time.LocalDate;

@Slf4j
@Service
public class OrdersService {
    private OrderRepository orderRepository;

    @Autowired
    public OrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> getOrdersByName(String username, Pageable pageable) {
        return orderRepository
                .findAllByUser_UsernameOrderByDateDesc(username, pageable);
    }

    public OrdersDTO getTodayOrdersByUserame(String username) {
        return new OrdersDTO(orderRepository
                .findAllByUser_UsernameAndDateOrderByTimeDesc(username, LocalDate.now())
        );
    }
}
