package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.repository.BillRepository;
import ua.restaurant.spring.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ua.restaurant.spring.domain.type.OrderStatement.*;

@RunWith( MockitoJUnitRunner.class )
public class AdminBillServiceTest {

    private static final Order CONFIRMED_ORDER = Order.builder()
            .id(1L)
            .orderStatement(CONFIRMED)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(5)
            .totalPrice(10)
            .build();

    private static final Order REJECTED_ORDER = Order.builder()
            .id(2L)
            .orderStatement(REJECTED)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(5)
            .totalPrice(10)
            .build();

    private static final Order INVOICED_ORDER = Order.builder()
            .id(3L)
            .orderStatement(INVOICED)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(5)
            .totalPrice(10)
            .build();

    private static final long ID_NOT_FOUND = 5;


    @InjectMocks
    private AdminBillService instance;

    @Mock
    private BillRepository billRepository;
    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        when(orderRepository.findById(CONFIRMED_ORDER.getId())).thenReturn(Optional.of(CONFIRMED_ORDER));
        when(orderRepository.findById(REJECTED_ORDER.getId())).thenReturn(Optional.of(REJECTED_ORDER));
        when(orderRepository.findById(INVOICED_ORDER.getId())).thenReturn(Optional.of(INVOICED_ORDER));
        doThrow(IdNotFoundException.class).when(orderRepository).findById(ID_NOT_FOUND);
    }

    @Test
    public void shouldReturnTrueWhenOrderWasNotInvoiced() throws IdNotFoundException {
        boolean result = instance.saveNewBill(CONFIRMED_ORDER.getId());
        Assert.assertTrue(result);
    }

    @Test
    public void shouldREturnFalseIfOrderIsRejected() throws IdNotFoundException {
        boolean result = instance.saveNewBill(REJECTED_ORDER.getId());
        Assert.assertFalse(result);
    }

    @Test
    public void shouldREturnFalseIfOrderIsInvoiced() throws IdNotFoundException {
        boolean result = instance.saveNewBill(INVOICED_ORDER.getId());
        Assert.assertFalse(result);
    }

    @Test( expected = IdNotFoundException.class )
    public void shouldThrowExceptionIfOrderIdNotFound() throws IdNotFoundException {
        instance.saveNewBill(ID_NOT_FOUND);
    }

}