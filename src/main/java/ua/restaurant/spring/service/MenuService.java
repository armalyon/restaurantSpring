package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.domain.MenuItem;
import ua.restaurant.spring.dto.MenuDTO;
import ua.restaurant.spring.repository.MenuItemRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private MenuItemRepository itemRepository;
    @Autowired
    public MenuService(MenuItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public MenuDTO getMenu(){
                return new MenuDTO(itemRepository
                        .findAllByStorageQuantityGreaterThan(0L));
    }

}
