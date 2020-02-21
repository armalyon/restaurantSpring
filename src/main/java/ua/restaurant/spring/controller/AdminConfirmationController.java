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
import org.springframework.web.bind.annotation.RequestParam;
import ua.restaurant.spring.domain.Order;
import ua.restaurant.spring.domain.type.OrderStatement;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.exceptions.NotEnoughItemsException;
import ua.restaurant.spring.service.AdminOrderConfirmationService;
import ua.restaurant.spring.service.AdminOrderService;
import ua.restaurant.spring.service.AdminOrderStatementService;

@Slf4j
@Controller
@RequestMapping( "/admin/confirmation" )
public class AdminConfirmationController {
    private final static String ADMIN_CONFIRMATION_PAGE = "adminconfirmation";
    private final static String ADMIN_CONFIRMATION_REDIRECT = "redirect:/admin/confirmation";
    private final static String ADMIN_CONFIRMATION_NOT_ENOUGH_REDIRECT = "redirect:/admin/confirmation?notenough";
    private final static String ADMIN_CONFIRMATION_ERROR_REDIRECT = "redirect:/admin/confirmation?error";

    private AdminOrderService adminOrderService;
    private AdminOrderConfirmationService adminOrderConfirmationService;
    private AdminOrderStatementService adminOrderStatementService;

    @Autowired
    public AdminConfirmationController(AdminOrderService adminOrderService,
                                       AdminOrderConfirmationService adminOrderConfirmationService,
                                       AdminOrderStatementService adminOrderStatementService) {
        this.adminOrderService = adminOrderService;
        this.adminOrderConfirmationService = adminOrderConfirmationService;
        this.adminOrderStatementService = adminOrderStatementService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String getConfirmationPage(@PageableDefault Pageable pageable, Model model) {
        Page<Order> page =
                adminOrderService
                        .getOrdersByStatement(OrderStatement.WAITING, pageable);
        model.addAttribute("page", page);
        return ADMIN_CONFIRMATION_PAGE;
    }

    @PostMapping( "/confirm" )
    public String confirmOrder(@RequestParam Long id, @RequestParam Long quantity) {
        try {
            if (adminOrderConfirmationService.isCanBeConfirmed(id))
                adminOrderConfirmationService.confirmOrder(id, quantity);
        } catch (IdNotFoundExeption e) {
            return handleNotFoundExc(e);
        } catch (NotEnoughItemsException e) {
            return handleNotEnoughItemsExc(e);
        }
        return ADMIN_CONFIRMATION_REDIRECT;
    }

    @PostMapping( "/reject" )
    public String rejectOrder(Long id) {
        adminOrderStatementService.updateOrderStatement(OrderStatement.REJECTED, id);
        return ADMIN_CONFIRMATION_REDIRECT;
    }


    private String handleNotFoundExc(Exception e) {
        log.warn(e.getMessage());
        return ADMIN_CONFIRMATION_ERROR_REDIRECT;
    }

    private String handleNotEnoughItemsExc(Exception e) {
        log.warn(e.getMessage());
        return ADMIN_CONFIRMATION_NOT_ENOUGH_REDIRECT;
    }

}
