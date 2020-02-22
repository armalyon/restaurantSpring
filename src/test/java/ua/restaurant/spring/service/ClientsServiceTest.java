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
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.repository.UserRepository;

import static org.mockito.Mockito.when;
import static ua.restaurant.spring.domain.type.Role.CLIENT;

@RunWith( MockitoJUnitRunner.class )
public class ClientsServiceTest {

    @InjectMocks
    private ClientsService instance;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Page<User> page;

    @Before
    public void setUp() {
        when(userRepository
                .findAllByRole(CLIENT, PageRequest.of(1, 2)))
                .thenReturn(page);
    }

    @Test
    public void shouldReturnPage() {
        Page<User> result = instance.getAllClients(PageRequest.of(1, 2));
        Assert.assertEquals(page, result);
    }
}