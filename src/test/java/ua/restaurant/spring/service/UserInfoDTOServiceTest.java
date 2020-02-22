package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.dto.UserInfoDTO;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.exceptions.UserNotFoundException;
import ua.restaurant.spring.repository.OrderRepository;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class UserInfoDTOServiceTest {
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    private final static String NAME = "Name";
    private final static String SURNAME = "Surname";
    private static final int ORDERS_NUMBER = 5;
    private static final long ID = 15;
    private static final long ID_NOT_FOUND = 25;

    private final static User USER = User.builder()
            .id(ID)
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME)
            .password(PASSWORD)
            .registrationDate(LocalDateTime.MIN)
            .funds(25)
            .role(Role.CLIENT)
            .build();

    private static final UserInfoDTO USER_INFO_DTO = UserInfoDTO.builder()
            .registrationDate(LocalDateTime.MIN)
            .ordersTotalNumber(ORDERS_NUMBER)
            .surname(SURNAME)
            .name(NAME)
            .username(USERNAME)
            .build();


    @InjectMocks
    private UserInfoDTOService instance;

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setUp(){
        when(orderRepository.countByUser_Id(ID)).thenReturn(ORDERS_NUMBER);
        when(userRepository.findById(ID)).thenReturn(Optional.of(USER));
        doThrow(UserNotFoundException.class).when(userRepository).findById(ID_NOT_FOUND);
    }

    @Test
    public void shouldReturnUserInfoDTOIfUserExists() throws IdNotFoundExeption {
        UserInfoDTO result = instance.getUserInfDTOById(ID);
        Assert.assertEquals(USER_INFO_DTO, result);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionWhenIdNotFound() throws IdNotFoundExeption {
        instance.getUserInfDTOById(ID_NOT_FOUND);
    }


}