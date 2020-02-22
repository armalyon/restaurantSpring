package ua.restaurant.spring.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.dto.MenuDTO;
import ua.restaurant.spring.repository.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.class )
public class MenuServiceTest {
    private static final MenuItem MENU_ITEM_1 = MenuItem.builder()
            .id(1)
            .storageQuantity(3)
            .name("name")
            .nameUA("nameUA")
            .price(5)
            .weight(4)
            .build();

    private static final MenuItem MENU_ITEM_2 = MenuItem.builder()
            .id(2)
            .storageQuantity(3)
            .name("name2")
            .nameUA("nameUA2")
            .price(5)
            .weight(4)
            .build();
    private List<MenuItem> items = new ArrayList<>();

    private MenuDTO menuDTO;

    @InjectMocks
    private MenuService instance;

    @Mock
    private MenuItemRepository itemRepository;

    @Before
    public void setUp() {
        items.add(MENU_ITEM_1);
        items.add(MENU_ITEM_2);
        menuDTO = new MenuDTO(items);
        when(itemRepository.findAllByStorageQuantityGreaterThan(0L)).thenReturn(items);
    }

    @Test
    public void shouldReturnMenuDTO() {
        MenuDTO result = instance.getMenu();
        Assert.assertEquals(menuDTO, result);
    }


}