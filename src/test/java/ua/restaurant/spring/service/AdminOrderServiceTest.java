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
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.repository.OrderRepository;

import static org.mockito.Mockito.when;
import static ua.restaurant.spring.domain.type.OrderStatement.WAITING;

@RunWith( MockitoJUnitRunner.class )
public class AdminOrderServiceTest {

    @Mock
    private static Page<Order> page;
    @InjectMocks
    private AdminOrderService instantance;
    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        when(orderRepository
                .findAllByOrderStatementOrderByDate(WAITING, PageRequest.of(1, 2)))
                .thenReturn(page);
    }

    @Test
    public void shouldReturnNotEmptyPage() {
        Page<Order> result = instantance.getOrdersByStatement(WAITING, PageRequest.of(1, 2));
        Assert.assertEquals(page, result);
    }
}