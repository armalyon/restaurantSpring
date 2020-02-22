package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.domain.UserAdapter;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static ua.restaurant.spring.domain.type.Role.CLIENT;

@RunWith( MockitoJUnitRunner.class )
public class UserServiceTest {

    private static final Role ROLE = CLIENT;
    private static final String USERNAME = "USERNAME";
    private static final String USERNAME_NOT_FOUND = "USERNAME_2";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final int FUNDS = 25;
    private static final User USER = User.builder()
            .id(11)
            .username(USERNAME)
            .name(NAME)
            .surname(SURNAME)
            .password(BCrypt.hashpw("pass1234", BCrypt.gensalt()))
            .funds(FUNDS)
            .role(ROLE)
            .registrationDate(LocalDateTime.MIN)
            .build();
    private static final UserDetails USER_DETAILS = new UserAdapter(USER);

    @InjectMocks
    private UserService instance;
    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(USER));
        doThrow(UsernameNotFoundException.class).when(userRepository).findByUsername(USERNAME_NOT_FOUND);
    }

    @Test
    public void shouldReturnUserRoleWhenUsernameExist() {
        Role result = instance.getUserRoleByUsername(USERNAME);
        Assert.assertEquals(ROLE, result);
    }

    @Test( expected = UsernameNotFoundException.class )
    public void shoulThrowExceptionWhenTryingToGetRoleAndUsernameNotFound() {
        instance.getUserRoleByUsername(USERNAME_NOT_FOUND);
    }

    @Test
    public void shouldReturnFundsWhenUsernameExists() {
        long funds = instance.getFundsByUsername(USERNAME);
        Assert.assertEquals(FUNDS, funds);
    }

    @Test( expected = UsernameNotFoundException.class )
    public void shoulThrowExceptionWhenTryingToGetFundsAndUsernameNotFound() {
        instance.getFundsByUsername(USERNAME_NOT_FOUND);
    }

    @Test
    public void shouldReturnUserDetailsWhenUserExists(){
        UserDetails result = instance.loadUserByUsername(USERNAME);
        Assert.assertEquals(USER_DETAILS, result);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExeptionWhenTryingLoadDetailsAndUsernameNotExists(){
        instance.loadUserByUsername(USERNAME_NOT_FOUND);
    }

}