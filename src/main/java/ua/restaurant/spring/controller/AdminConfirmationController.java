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
import ua.restaurant.spring.domain.types.OrderStatement;
import ua.restaurant.spring.dto.OrdersDTO;
import ua.restaurant.spring.exceptions.NotEnoughItemsException;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.service.AdminOrderService;
import ua.restaurant.spring.service.OrderConfirmationService;

@Slf4j
@Controller
@RequestMapping( "/admin/confirmation" )
public class AdminConfirmationController {
    private final static String ADMIN_CONFIRMATION_PAGE = "adminconfirmation";
    private final static String ADMIN_CONFIRMATION_REDIRECT = "redirect:/admin/confirmation";
    private final static String ADMIN_CONFIRMATION_NOT_ENOUGH_REDIRECT = "redirect:/admin/confirmation?notenough";
    private final static String ADMIN_CONFIRMATION_ERROR_REDIRECT = "redirect:/admin/confirmation?error";

    private AdminOrderService adminOrderService;
    private OrderConfirmationService orderConfirmationService;

    @Autowired
    public AdminConfirmationController(AdminOrderService adminOrderService,
                                       OrderConfirmationService orderConfirmationService) {
        this.adminOrderService = adminOrderService;
        this.orderConfirmationService = orderConfirmationService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String getConfirmationPage(@PageableDefault Pageable pageable, Model model){
        Page<Order> waitingOrders =
                adminOrderService
                        .getOrdersByStatement(OrderStatement.WAITING, pageable);
        model.addAttribute("waiting", waitingOrders);
        return ADMIN_CONFIRMATION_PAGE;
    }

    @PostMapping( "/confirmorder" )
    public String confirmOrder(@RequestParam Long id, @RequestParam Long quantity) {
        try {
            if (orderConfirmationService.isCanBeConfirmed(id))
                                       orderConfirmationService.confirmOrder(id, quantity);
        } catch (IdNotFoundExeption e) {
            return handleNotFoundExc(e);
        } catch (NotEnoughItemsException e) {
            return handleNotEnoughItemsExc(e);
        }
        return ADMIN_CONFIRMATION_REDIRECT;
    }

    @PostMapping( "/rejectorder" )
    public String rejectOrder(Long id) {
        orderConfirmationService.updateOrderStatement(OrderStatement.REJECTED, id);
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
