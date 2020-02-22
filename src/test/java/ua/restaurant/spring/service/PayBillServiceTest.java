package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.exceptions.NotEnoughFundsException;
import ua.restaurant.spring.exceptions.UserNotFoundException;
import ua.restaurant.spring.repository.BillRepository;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static ua.restaurant.spring.domain.type.BillStatement.INVOICE;
import static ua.restaurant.spring.domain.type.BillStatement.PAYED;
import static ua.restaurant.spring.domain.type.OrderStatement.INVOICED;

@RunWith( MockitoJUnitRunner.class )
public class PayBillServiceTest {

    private static final String USERNAME = "username";
    private static final String ADMIN_USERNAME = "admin";
    private static final String USERNAME_NO_FUNDS = "username3";
    private static final String USERNAME_2 = "username2";
    private static final long BILL_ID = 1;
    private static final long BILL_PAYED_ID = 3;
    private static final long BILL_NOT_FOUND_ID = 5;

    private static final User USER = User.builder()
            .username(USERNAME)
            .id(21L)
            .funds(10)
            .build();

    private static final User ADMIN = User.builder()
            .username(ADMIN_USERNAME)
            .id(21L)
            .funds(10)
            .build();

    private static final User USER_FUNDS_NOT_ENOUGH = User.builder()
            .username(USERNAME_NO_FUNDS)
            .id(21L)
            .funds(9)
            .build();

    private static final Order ORDER = Order.builder()
            .orderStatement(INVOICED)
            .quantity(5)
            .user(USER)
            .totalPrice(10)
            .id(11)
            .build();


    private static final Bill BILL = Bill.builder()
            .id(BILL_ID)
            .statement(INVOICE)
            .invoiceDateTime(LocalDateTime.MIN)
            .order(ORDER)
            .build();

    private static final Bill BILL_PAYED = Bill.builder()
            .id(BILL_PAYED_ID)
            .statement(PAYED)
            .invoiceDateTime(LocalDateTime.MIN)
            .order(ORDER)
            .build();

    @InjectMocks
    private PayBillService instance;

    @Mock
    private BillRepository billRepository;
    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        when(userRepository.findByUsername(USER.getUsername())).thenReturn(Optional.of(USER));
        when(userRepository.findByUsername(ADMIN.getUsername())).thenReturn(Optional.of(ADMIN));
        when(userRepository.findByUsername(USER_FUNDS_NOT_ENOUGH.getUsername())).thenReturn(Optional.of(USER_FUNDS_NOT_ENOUGH));
        when(billRepository.findById(BILL.getId())).thenReturn(Optional.of(BILL));
        when(billRepository.findById(BILL_PAYED.getId())).thenReturn(Optional.of(BILL_PAYED));
        doThrow(IdNotFoundExeption.class).when(billRepository).findById(BILL_NOT_FOUND_ID);
        doThrow(UserNotFoundException.class).when(userRepository).findByUsername(USERNAME_2);
    }

    @Test
    public void shouldReturnTrueWhenBillNotPayedAndFundsEnough()
            throws UserNotFoundException, IdNotFoundExeption, NotEnoughFundsException {
        boolean result = instance.payBill(BILL.getId(), USER.getUsername());
        Assert.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenBillPayed()
            throws UserNotFoundException, IdNotFoundExeption, NotEnoughFundsException {
        boolean result = instance.payBill(BILL_PAYED.getId(), USER.getUsername());
        Assert.assertFalse(result);
    }

    @Test(expected = NotEnoughFundsException.class)
    public void shouldThrowExeptionWhenUserFundsNotEnough()
            throws UserNotFoundException, IdNotFoundExeption, NotEnoughFundsException {
        instance.payBill(BILL.getId(), USER_FUNDS_NOT_ENOUGH.getUsername());
    }

    @Test(expected = IdNotFoundExeption.class)
    public void shouldThrowExceptionWhenBillIdNotFound()
            throws UserNotFoundException, IdNotFoundExeption, NotEnoughFundsException {
        instance.payBill(BILL_NOT_FOUND_ID, USER.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionWhenUsernameNotFound()
            throws UserNotFoundException, IdNotFoundExeption, NotEnoughFundsException {
        instance.payBill(BILL.getId(), USERNAME_2);
    }
}