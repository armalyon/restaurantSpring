package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.exceptions.NotEnoughItemsException;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.repository.MenuItemRepository;
import ua.restaurant.spring.repository.OrderRepository;

import javax.transaction.Transactional;

import static ua.restaurant.spring.domain.type.OrderStatement.CONFIRMED;

@Service
public class AdminOrderConfirmationService {
    private OrderRepository orderRepository;
    private MenuItemRepository menuItemRepository;

    @Autowired
    public AdminOrderConfirmationService(OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Transactional
    public void confirmOrder(Long orderId, Long quantity) throws IdNotFoundExeption {
        menuItemRepository.decreaseStorageQuantityById(
                getOrederById(orderId).getMenuItem().getId(), quantity);
        orderRepository.updateOrderStatementById(CONFIRMED, orderId);

    }

    public boolean isCanBeConfirmed(Long orderId) throws IdNotFoundExeption, NotEnoughItemsException {
        Order order = getOrederById(orderId);
        if (order.getMenuItem()
                .getStorageQuantity()
                <= order.getQuantity()) throw new NotEnoughItemsException("Not enough goods");

        return !order.getOrderStatement().equals(CONFIRMED);
    }

    private Order getOrederById(Long id) throws IdNotFoundExeption {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundExeption("order not found", id));
    }

}
