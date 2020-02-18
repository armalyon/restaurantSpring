package ua.restaurant.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.domain.User;
import ua.restaurant.spring.service.ClientsService;

import static ua.restaurant.spring.service.utility.Constants.REGISTRATION_DATE_FIELD;


@Controller
@RequestMapping( "/admin/stats" )
public class AdminStatsPageController {
    private static final String ADMIN_USERS_PAGE = "adminusers";
    private ClientsService clientsService;

    @Autowired
    public AdminStatsPageController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String getstatsPage(@PageableDefault( sort = REGISTRATION_DATE_FIELD, direction = Sort.Direction.DESC, size = 5 ) Pageable pageable,
                               Model model) {
        int clientsNumber = clientsService.getClientCount();
        Page<User> page =
                clientsService
                        .getAllClients(pageable);
        model.addAttribute("page", page);
        model.addAttribute("clientsNumber", clientsNumber);
        return ADMIN_USERS_PAGE;
    }

}
