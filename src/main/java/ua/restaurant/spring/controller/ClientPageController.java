package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.dto.MenuDTO;
import ua.restaurant.spring.dto.OrderDTO;
import ua.restaurant.spring.dto.OrdersDTO;
import ua.restaurant.spring.exception.IdNotFoundException;
import ua.restaurant.spring.exception.NotEnoughItemsException;
import ua.restaurant.spring.exception.UserNotFoundException;
import ua.restaurant.spring.service.ClientOrderService;
import ua.restaurant.spring.service.MenuService;
import ua.restaurant.spring.service.OrdersDTOService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping( "/user" )
public class ClientPageController {
    private final static String QUANTITY_NOT_ENOUGH_REDIRECT = "redirect:/user?quantity=notenough";
    private final static String LOGOUT_REDIRECT = "redirect:/logout";
    private final static String ITEM_NOT_FOUND_REDIRECT = "redirect:/user?item=notfound";
    private final static String USER_REDIRECT = "redirect:/user";
    private final static String CLIENTPAGE_NAME = "clientpage";

    private MenuService menuService;
    private OrdersDTOService ordersDTOService;
    private ClientOrderService clientOrderService;

    @Autowired
    public ClientPageController(MenuService menuService,
                                OrdersDTOService ordersDTOService,
                                ClientOrderService clientOrderService) {
        this.menuService = menuService;
        this.ordersDTOService = ordersDTOService;
        this.clientOrderService = clientOrderService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('CLIENT')" )
    public String getMainPage(Principal principal, Model model) {
        MenuDTO menu = menuService.getMenu();
        OrdersDTO ordersDTO = ordersDTOService.getTodayOrdersByUserame(principal.getName());
        model.addAttribute("todayOrders", ordersDTO);
        model.addAttribute("menuDTO", menu);
        model.addAttribute("orderDTO", new OrderDTO());
        return CLIENTPAGE_NAME;
    }

    @PostMapping( "/order" )
    public String getOrder(@ModelAttribute OrderDTO orderDTO, Principal principal) {
        try {
            clientOrderService.saveNewOrder(principal.getName(), orderDTO);
        } catch (NotEnoughItemsException e) {
            return handleNotEnoughItemsExc(e);
        } catch (UserNotFoundException e) {
            return handleUserNotFoundExc(e);
        } catch (IdNotFoundException e) {
            return handleIdNotFoundExc(e);
        }
        return USER_REDIRECT;
    }


    private String handleNotEnoughItemsExc(NotEnoughItemsException e) {
        log.error(e.getMessage());
        return QUANTITY_NOT_ENOUGH_REDIRECT;
    }

    private String handleUserNotFoundExc(UserNotFoundException e) {
        log.error(e.getMessage());
        return LOGOUT_REDIRECT;
    }

    private String handleIdNotFoundExc(IdNotFoundException e) {
        log.error("Error: {}, id= {}", e.getMessage(), e.getId());
        return ITEM_NOT_FOUND_REDIRECT;
    }


}
