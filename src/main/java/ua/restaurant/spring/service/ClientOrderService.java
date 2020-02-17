package ua.restaurant.spring.service;

import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.dto.OrderDTO;
import ua.restaurant.spring.exceptions.ItemNotFoundException;
import ua.restaurant.spring.exceptions.NotEnoughItemsException;
import ua.restaurant.spring.exceptions.UserNotFoundException;
import ua.restaurant.spring.repository.MenuItemRepository;
import ua.restaurant.spring.repository.OrderRepository;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ClientOrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private MenuItemRepository menuItemRepository;

    public ClientOrderService(OrderRepository orderRepository,
                              UserRepository userRepository,
                              MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public void saveNewOrder(String username, OrderDTO orderDTO) throws NotEnoughItemsException,
            UserNotFoundException, ItemNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found during creating of a new order", username));

        if (!isItemsEnough(orderDTO)) {
            throw new NotEnoughItemsException("Not enough items");
        }
        Order order = createOrder(orderDTO, user);
        orderRepository.save(order);
    }

    private Order createOrder(OrderDTO orderDTO, User user) {
        return Order.builder()
                .menuItem(orderDTO.getMenuItem())
                .quantity(orderDTO.getQuantity())
                .totalPrice(getTotalPrice(orderDTO))
                .date(LocalDate.now())
                .time(LocalTime.now())
                .user(user)
                .orderStatement(OrderStatement.WAITING)
                .build();
    }

    private boolean isItemsEnough(OrderDTO orderDTO) throws ItemNotFoundException {
        return orderDTO.getQuantity()
                <=
                menuItemRepository.findByName(
                        orderDTO.getMenuItem()
                                .getName())
                        .orElseThrow(() -> new ItemNotFoundException("menu item  not found" +
                                orderDTO.getMenuItem().getName()))
                        .getStorageQuantity();
    }

    private long getTotalPrice(OrderDTO orderDTO) {
        return orderDTO
                .getMenuItem()
                .getPrice() * orderDTO.getQuantity();
    }


}
