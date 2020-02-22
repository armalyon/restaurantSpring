package ua.restaurant.spring.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.dto.OrderDTO;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.exception.NotEnoughItemsException;
import ua.restaurant.spring.exception.UserNotFoundException;
import ua.restaurant.spring.repository.MenuItemRepository;
import ua.restaurant.spring.repository.OrderRepository;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static ua.restaurant.spring.domain.type.Role.CLIENT;

@RunWith( MockitoJUnitRunner.class )
public class ClientOrderServiceTest {
    private static final String USERNAME = "username";
    private static final long MENU_ITEM_ID = 11;
    private static final long MENU_ITEM_ID_NOT_FOUND = 111;
    private static final long QUANTITY_ENOUGH = 5;
    private static final long QUANTITY_NOT_ENOUGH = 6;
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String NAME_UA = "NAME_UA";
    private static final String USERNAME_NOT_FOUND = "USERNAME_NOT_FOUND";

    private static final User USER = User.builder()
            .id(21L)
            .funds(10)
            .name(NAME)
            .surname(SURNAME)
            .registrationDate(LocalDateTime.MIN)
            .role(CLIENT)
            .username(USERNAME)
            .password(PASSWORD)
            .build();

    private static final MenuItem MENU_ITEM = MenuItem.builder()
            .id(MENU_ITEM_ID)
            .storageQuantity(5)
            .name(NAME)
            .nameUA(NAME_UA)
            .price(10L)
            .weight(10)
            .build();

    private static final OrderDTO ORDER_DTO = OrderDTO.builder()
            .menuItemId(MENU_ITEM_ID)
            .quantity(QUANTITY_ENOUGH)
            .build();

    private static final OrderDTO ORDER_DTO_NOT_FOUND = OrderDTO.builder()
            .menuItemId(MENU_ITEM_ID_NOT_FOUND)
            .quantity(QUANTITY_ENOUGH)
            .build();

    private static final OrderDTO ORDER_DTO_QUANTITY_NOT_ENOUGH = OrderDTO.builder()
            .menuItemId(MENU_ITEM_ID)
            .quantity(QUANTITY_NOT_ENOUGH)
            .build();


    @InjectMocks
    private ClientOrderService instance;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        when(menuItemRepository.findById(MENU_ITEM_ID)).thenReturn(Optional.of(MENU_ITEM));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(USER));
        doThrow(UserNotFoundException.class).when(userRepository).findByUsername(USERNAME_NOT_FOUND);
        doThrow(IdNotFoundException.class).when(menuItemRepository).findById(MENU_ITEM_ID_NOT_FOUND);
    }

    @Test( expected = Test.None.class )
    public void shouldNotThrowAnyExceptionWhenOrderSaved()
            throws UserNotFoundException, NotEnoughItemsException, IdNotFoundException {
        instance.saveNewOrder(USERNAME, ORDER_DTO);
        verify(orderRepository).save(Mockito.any());
    }

    @Test( expected = UserNotFoundException.class )
    public void shouldThrowUserNotFoundExceptionWhenUsernameNotFound()
            throws UserNotFoundException, NotEnoughItemsException, IdNotFoundException {
        instance.saveNewOrder(USERNAME_NOT_FOUND, ORDER_DTO);
    }

    @Test( expected = IdNotFoundException.class )
    public void shouldThrowIdNotFoundExceptionWhenMenuItemIdNotFound()
            throws UserNotFoundException, NotEnoughItemsException, IdNotFoundException {
        instance.saveNewOrder(USERNAME, ORDER_DTO_NOT_FOUND);
    }

    @Test( expected = NotEnoughItemsException.class )
    public void shouldThrwNotEnoughItemsExceptionWhenStorageQuantityNotEnough() throws UserNotFoundException, NotEnoughItemsException, IdNotFoundException {
        instance.saveNewOrder(USERNAME, ORDER_DTO_QUANTITY_NOT_ENOUGH);
    }
}