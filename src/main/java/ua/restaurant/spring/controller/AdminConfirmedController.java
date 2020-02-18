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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.service.AdminOrderService;
import ua.restaurant.spring.service.AdminBillService;

@Slf4j
@Controller
@RequestMapping( "/admin/confirmed" )
public class AdminConfirmedController {
    private final static String ADMIN_CONFIRMED_PAGE = "adminconfirmed";
    private final static String ADMIN_CONFIRMED_REDIRECT = "redirect:/admin/confirmed";
    private final static String ADMIN_CONFIRMED_ORDER_NOT_FOUND_REDIRECT =
            "redirect:/admin/confirmed?order=notfound";

    private AdminOrderService adminOrderService;
    private AdminBillService adminBillService;

    @Autowired
    public AdminConfirmedController(AdminOrderService adminOrderService, AdminBillService adminBillService) {
        this.adminOrderService = adminOrderService;
        this.adminBillService = adminBillService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String getConfirmedOrrdersPage(@PageableDefault Pageable pageable, Model model) {
        Page<Order> page =
                adminOrderService
                        .getOrdersByStatement(OrderStatement.CONFIRMED, pageable);
        model.addAttribute("page", page);
        return ADMIN_CONFIRMED_PAGE;
    }

    @PostMapping( "/bill" )
    public String billByOrder(Long id) {
        try {
            adminBillService.saveNewBill(id);
        } catch (IdNotFoundExeption e) {
            return handleNotFoundExc(e);
        }
        return ADMIN_CONFIRMED_REDIRECT;
    }


    private String handleNotFoundExc(Exception e) {
        log.warn(e.getMessage());
        return ADMIN_CONFIRMED_ORDER_NOT_FOUND_REDIRECT;
    }

}
