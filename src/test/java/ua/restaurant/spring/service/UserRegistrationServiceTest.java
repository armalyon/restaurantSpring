package ua.restaurant.spring.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.dto.AccountDTO;
import ua.restaurant.spring.exception.UserExistsException;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith( MockitoJUnitRunner.class )
public class UserRegistrationServiceTest {
    private final static String USERNAME = "username";
    private final static String USERNAME_REGISTERED = "username2";
    private final static String PASSWORD = "password";
    private final static String NAME = "Name";
    private final static String SURNAME = "Surname";

    private final static User USER = User.builder()
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME_REGISTERED)
            .password(PASSWORD)
            .registrationDate(LocalDateTime.now())
            .funds(25)
            .role(Role.CLIENT)
            .build();

    private final static AccountDTO VALID_ACCOUNT_DTO = AccountDTO.builder()
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME)
            .password(PASSWORD)
            .passwordConfirmation(PASSWORD)
            .build();

    private final static AccountDTO INVALID_ACCOUNT_DTO = AccountDTO.builder()
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME_REGISTERED)
            .password(PASSWORD)
            .passwordConfirmation(PASSWORD)
            .build();

    @InjectMocks
    private UserRegistrationService instance;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);
        doThrow(DataIntegrityViolationException.class).when(userRepository).save(USER);
    }

    @Test(expected = Test.None.class )
    public void shouldNotThrowExceptionWhenUsernameValid() throws UserExistsException {
        instance.saveNewUser(VALID_ACCOUNT_DTO);
    }
    @Test(expected = UserExistsException.class )
    public void shouldThrowExceptionWhenUsernameExists() throws UserExistsException {
        instance.saveNewUser(INVALID_ACCOUNT_DTO);
    }
}