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

    @PostConstruct
    public void addMenuItemsToDB() {
        MenuItem item = MenuItem.builder()
                .name("Pizza")
                .nameUA("Піцца")
                .weight(400)
                .storageQuantity(0)
                .price(10)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Apple juice")
                .nameUA("Яблучний сік")
                .weight(500)
                .storageQuantity(10)
                .price(3)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Vodka")
                .nameUA("Горілка")
                .weight(700)
                .storageQuantity(4)
                .price(10)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Cutlet")
                .nameUA("Котлета")
                .weight(200)
                .storageQuantity(8)
                .price(7)
                .build();
        menuItemRepository.save(item);

        item = MenuItem.builder()
                .name("Pelmeni")
                .nameUA("Пельмені")
                .weight(300)
                .storageQuantity(2)
                .price(10)
                .build();
        menuItemRepository.save(item);
    }
}
