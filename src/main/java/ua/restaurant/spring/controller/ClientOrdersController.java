package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.service.OrdersService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping( "/user/orders" )
public class ClientOrdersController {
    private static final String CLIENT_ORDERS_PAGE = "clientorders";
    private OrdersService ordersService;

    @Autowired
    public ClientOrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('CLIENT')" )
    public String getOrdersPage(@PageableDefault Pageable pageable, Principal principal, Model model) {
        Page<Order> page = ordersService
                .getOrdersByName(principal.getName(), pageable);
        model.addAttribute("page", page);
        return CLIENT_ORDERS_PAGE;
    }

}
