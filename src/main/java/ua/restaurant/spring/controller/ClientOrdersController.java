package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.dto.OrdersDTO;
import ua.restaurant.spring.service.OrdersDTOService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/user/orders")
public class ClientOrdersController {
    private static final String CLIENT_ORDERS_PAGE = "clientorders";
    private OrdersDTOService ordersDTOService;

    @Autowired
    public ClientOrdersController(OrdersDTOService ordersDTOService) {
        this.ordersDTOService = ordersDTOService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('CLIENT')" )
    public String getOrdersPage(Principal principal, Model model){
        OrdersDTO ordersDTO = ordersDTOService
                .getOrdersByName(principal.getName());
        model.addAttribute("orders", ordersDTO);
        return CLIENT_ORDERS_PAGE;
    }

}
