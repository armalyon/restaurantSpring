package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.dto.BillsDTO;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.exceptions.NotEnoughFundsException;
import ua.restaurant.spring.exceptions.UserNotFoundException;
import ua.restaurant.spring.service.ClientBillsService;
import ua.restaurant.spring.service.PayBillService;
import ua.restaurant.spring.service.UserService;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping( "/user/bills" )
public class ClientBillsController {
    private final static String CLIENT_BILLS_PAGE = "clientbills";
    private final static String USER_BILLS_FUNDS_ERROR_REDIRECT = "redirect:/user/bills?fnds=error";
    private final static String USER_BILLS_NOTFOUND_ERROR_REDIRECT = "redirect:/user/bills?idnotfound";
    private final static String USER_BILLS_REDIRECT = "redirect:/user/bills";

    private ClientBillsService clientBillsService;
    private PayBillService payBillService;
    private UserService userService;

    @Autowired
    public ClientBillsController(ClientBillsService clientBillsService,
                                 PayBillService payBillService,
                                 UserService userService) {
        this.clientBillsService = clientBillsService;
        this.payBillService = payBillService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('CLIENT')" )
    public String getBills(Principal principal,
                           Model model) {
        BillsDTO billsDTO = clientBillsService
                .getBillsByUserNameNewestFirst(
                        principal.getName()
                );
        Long funds = userService
                .getFundsByUsername(principal.getName());
        model.addAttribute("funds", funds);
        model.addAttribute("bills", billsDTO);
        return CLIENT_BILLS_PAGE;
    }

    @PostMapping( "/pay" )
    public String payBill(Long id, Principal principal) {
        try {
            payBillService.payBill(id, principal.getName());
        } catch (NotEnoughFundsException e) {
            return handleNotEnoughFundsExc(e);
        } catch (IdNotFoundExeption e) {
            return handleIdNotFoundExc(e);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        return USER_BILLS_REDIRECT;
    }


    private String handleIdNotFoundExc(IdNotFoundExeption e) {
        log.error(e.getId() + e.getMessage());
        return USER_BILLS_NOTFOUND_ERROR_REDIRECT;
    }

    private String handleNotEnoughFundsExc( NotEnoughFundsException e){
        log.warn(e.getMessage() + "bill id=" + e.getBillId() + " difference=" + e.getFoundsDifference());
        return USER_BILLS_FUNDS_ERROR_REDIRECT;
    }
}
