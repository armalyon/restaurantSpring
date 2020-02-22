package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.dto.OrdersDTO;
import ua.restaurant.spring.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static ua.restaurant.spring.domain.type.OrderStatement.WAITING;

@RunWith( MockitoJUnitRunner.class )
public class OrdersServiceTest {
    private static final String USERNAME = "username";
    private static final MenuItem MENU_ITEM_1 = MenuItem.builder()
            .id(1)
            .storageQuantity(3)
            .name("name")
            .nameUA("nameUA")
            .price(5)
            .weight(4)
            .build();
    private static final Order ORDER_1 = Order.builder()
            .id(11L)
            .menuItem(MENU_ITEM_1)
            .orderStatement(WAITING)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(5)
            .totalPrice(10)
            .build();

    private static final Order ORDER_2 = Order.builder()
            .id(12L)
            .menuItem(MENU_ITEM_1)
            .orderStatement(WAITING)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(5)
            .totalPrice(10)
            .build();


    private OrdersDTO ordersDTO;
    private List<Order> orders = new ArrayList<>();

    @InjectMocks
    private OrdersService instance;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private Page<Order> page;

    @Before
    public void setUp() {
        orders.add(ORDER_1);
        orders.add(ORDER_2);
        ordersDTO = new OrdersDTO(orders);
        when(orderRepository
                .findAllByUser_UsernameOrderByDateDesc(USERNAME, PageRequest.of(1, 2)))
                .thenReturn(page);
        when(orderRepository
                .findAllByUser_UsernameAndDateOrderByTimeDesc(any(), any()))
                .thenReturn(orders);
    }

    @Test
    public void shouldReturnPageIfDataValid() {
        Page<Order> result = instance.getOrdersByName(USERNAME, PageRequest.of(1, 2));
        Assert.assertEquals(page, result);
    }

    @Test
    public void shouldReturnOrdersDTOIfDataValid() {
        OrdersDTO result = instance.getTodayOrdersByUserame(USERNAME);
        Assert.assertEquals(ordersDTO, result);
    }


}