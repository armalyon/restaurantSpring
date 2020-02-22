package ua.restaurant.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.repository.OrderRepository;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith( MockitoJUnitRunner.class )
public class AdminOrderStatementServiceTest {

    @InjectMocks
    private AdminOrderStatementService instance;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void shouldCallUpdateOrderStatementMethod() {
        instance.updateOrderStatement(OrderStatement.CONFIRMED, 11L);
        verify(orderRepository, atLeast(1)).updateOrderStatementById(OrderStatement.CONFIRMED, 11L);

    }
}