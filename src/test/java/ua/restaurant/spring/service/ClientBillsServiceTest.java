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
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.repository.BillRepository;

import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class ClientBillsServiceTest {

    private static final String USERNAME = "CLIENT";
    private static final long ID = 11L;
    @InjectMocks
    private ClientBillsService instance;
    @Mock
    private BillRepository billRepository;

    @Mock
    private Page<Bill> page;

    @Before
    public void setUp() {
        when(billRepository
                .findAllByOrder_User_UsernameOrderByInvoiceDateTimeDesc(USERNAME, PageRequest.of(1, 2)))
                .thenReturn(page);
        when(billRepository
                .findAllByOrder_User_IdOrderByOrder_DateDesc(ID, PageRequest.of(1, 2)))
                .thenReturn(page);
    }

    @Test
    public void shouldReturnPageByUsernameAndPagaeable() {
        Page<Bill> result = instance.getBillsByUserNameNewestFirst(USERNAME, PageRequest.of(1, 2));
        Assert.assertEquals(page, result);
    }

    @Test
    public void shouldReturnPageByUserIdPageable() {
        Page<Bill> result = instance.getBillsByUserIdNewestFirst(ID, PageRequest.of(1, 2));
        Assert.assertEquals(page, result);
    }
}