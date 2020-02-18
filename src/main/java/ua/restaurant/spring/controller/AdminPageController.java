package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.dto.MenuDTO;
import ua.restaurant.spring.service.MenuService;

@Slf4j
@Controller
@RequestMapping( "/admin" )
public class AdminPageController {
    private MenuService menuService;

    @Autowired
    public AdminPageController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String getAdminPage(Model model) {
        MenuDTO menu =
                menuService
                        .getMenu();
        model.addAttribute("menuDTO", menu);
        return "adminpage";
    }

}
