package ua.restaurant.spring.service;

import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.repository.OrderRepository;

import javax.transaction.Transactional;

@Service
public class AdminOrderStatementService {

    private OrderRepository orderRepository;

    public AdminOrderStatementService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public boolean updateOrderStatement(OrderStatement statement, Long orderId) {
        try {
            orderRepository.updateOrderStatementById(statement, orderId);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
