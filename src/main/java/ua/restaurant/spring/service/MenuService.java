package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.dto.MenuDTO;
import ua.restaurant.spring.repository.MenuItemRepository;

@Service
public class MenuService {
    private MenuItemRepository itemRepository;

    @Autowired
    public MenuService(MenuItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public MenuDTO getMenu() {
        return new MenuDTO(itemRepository
                .findAllByStorageQuantityGreaterThan(0L));
    }

}
