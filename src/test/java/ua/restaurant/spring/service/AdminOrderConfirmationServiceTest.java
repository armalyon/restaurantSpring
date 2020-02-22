package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.exception.NotEnoughItemsException;
import ua.restaurant.spring.repository.MenuItemRepository;
import ua.restaurant.spring.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ua.restaurant.spring.domain.type.OrderStatement.CONFIRMED;
import static ua.restaurant.spring.domain.type.OrderStatement.WAITING;

@RunWith( MockitoJUnitRunner.class )
public class AdminOrderConfirmationServiceTest {
    private static final long ID_NOT_FOUND = 38;

    private static final MenuItem MENU_ITEM = MenuItem.builder()
            .id(11L)
            .storageQuantity(5)
            .build();

    private static final MenuItem MENU_ITEM_LOW_QUANTITY = MenuItem.builder()
            .id(12L)
            .storageQuantity(4)
            .build();

    private static final Order WAITING_ORDER = Order.builder()
            .id(1L)
            .menuItem(MENU_ITEM)
            .orderStatement(WAITING)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(5)
            .totalPrice(10)
            .build();

    private static final Order CONFIRMED_ORDER = Order.builder()
            .id(2L)
            .menuItem(MENU_ITEM)
            .orderStatement(CONFIRMED)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(2)
            .totalPrice(10)
            .build();

    private static final Order TOO_MANY_ORDERED_ORDER = Order.builder()
            .id(3L)
            .menuItem(MENU_ITEM_LOW_QUANTITY)
            .orderStatement(WAITING)
            .date(LocalDate.now())
            .time(LocalTime.NOON)
            .quantity(6)
            .totalPrice(10)
            .build();

    @InjectMocks
    private AdminOrderConfirmationService instance;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        when(orderRepository.findById(WAITING_ORDER.getId())).thenReturn(Optional.of(WAITING_ORDER));
        when(orderRepository.findById(TOO_MANY_ORDERED_ORDER.getId())).thenReturn(Optional.of(TOO_MANY_ORDERED_ORDER));
        when(orderRepository.findById(CONFIRMED_ORDER.getId())).thenReturn(Optional.of(CONFIRMED_ORDER));
        doThrow(IdNotFoundException.class).when(orderRepository).findById(ID_NOT_FOUND);
    }

    @Test
    public void shouldCanBeConfirmedIfOrderHasWaitingStatementAndStorageQuantityEnough() throws IdNotFoundException, NotEnoughItemsException {
        boolean result = instance.isCanBeConfirmed(WAITING_ORDER.getId());
        Assert.assertTrue(result);
    }

    @Test( expected = NotEnoughItemsException.class )
    public void shouldThrowExceptionIfNotEnoughStorageQantity() throws IdNotFoundException, NotEnoughItemsException {
        instance.isCanBeConfirmed(TOO_MANY_ORDERED_ORDER.getId());
    }

    @Test
    public void shouldReturnFalseIfOrderIsConfirmed() throws IdNotFoundException, NotEnoughItemsException {
        boolean result = instance.isCanBeConfirmed(CONFIRMED_ORDER.getId());
        Assert.assertFalse(result);
    }

    @Test( expected = IdNotFoundException.class )
    public void shouldThrowExceptionIfOrderNotFound() throws IdNotFoundException {
        instance.confirmOrder(ID_NOT_FOUND, WAITING_ORDER.getQuantity());
    }

    @Test( expected = Test.None.class )
    public void shouldNotThrowExceptionWhenTyingToConfirmOrderIfOrderIsCorrect() throws IdNotFoundException {
        instance.confirmOrder(WAITING_ORDER.getId(), WAITING_ORDER.getQuantity());
    }
}