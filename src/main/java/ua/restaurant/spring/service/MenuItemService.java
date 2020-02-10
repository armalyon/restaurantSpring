package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.repository.MenuItemRepository;

import javax.annotation.PostConstruct;

@Service
public class MenuItemService {
    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

  //  @PostConstruct
    public void addMenuItemsToDB() {
        MenuItem item = MenuItem.builder()
                .name("Pizza 4 cheeses")
                .nameUA("Піца 4 сири")
                .weight(500)
                .storageQuantity(15)
                .price(10)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Pizza Margarita")
                .nameUA("Піца Маргарита")
                .weight(500)
                .storageQuantity(10)
                .price(13)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Pizza Carbonara")
                .nameUA("Піца Карбонара")
                .weight(500)
                .storageQuantity(4)
                .price(9)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Pizza Hawaiian")
                .nameUA("Піца Гавайська")
                .weight(400)
                .storageQuantity(8)
                .price(10)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Pizza Pepperoni")
                .nameUA("Піца Пепероні")
                .weight(400)
                .storageQuantity(2)
                .price(12)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Apple juice")
                .nameUA("Яблучний сік")
                .weight(200)
                .storageQuantity(2)
                .price(2)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Orange juice")
                .nameUA("Апельсиновий сік")
                .weight(200)
                .storageQuantity(20)
                .price(2)
                .build();
        menuItemRepository.save(item);
    }


}
