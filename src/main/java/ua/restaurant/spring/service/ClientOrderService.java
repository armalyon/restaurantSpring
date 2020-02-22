package ua.restaurant.spring.service;

import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.dto.OrderDTO;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.exception.NotEnoughItemsException;
import ua.restaurant.spring.exception.UserNotFoundException;
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
            UserNotFoundException, IdNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found during creating of a new order", username));
        MenuItem menuItem = menuItemRepository
                .findById(orderDTO.getMenuItemId())
                .orElseThrow(() -> new IdNotFoundException("menu item not found by id", orderDTO.getMenuItemId()));
        if (!isItemsEnough(orderDTO, menuItem)) {
            throw new NotEnoughItemsException("Not enough items");
        }
        Order order = createOrder(orderDTO, user, menuItem);
        orderRepository.save(order);
    }

    private Order createOrder(OrderDTO orderDTO, User user, MenuItem menuItem) {
        return Order.builder()
                .menuItem(menuItem)
                .quantity(orderDTO.getQuantity())
                .totalPrice(getTotalPrice(orderDTO, menuItem))
                .date(LocalDate.now())
                .time(LocalTime.now())
                .user(user)
                .orderStatement(OrderStatement.WAITING)
                .build();
    }

    private boolean isItemsEnough(OrderDTO orderDTO, MenuItem menuItem) {
        return orderDTO.getQuantity()
                <=
                menuItem.getStorageQuantity();
    }

    private long getTotalPrice(OrderDTO orderDTO, MenuItem menuItem) {
        return menuItem.getPrice() * orderDTO.getQuantity();
    }


}
