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
import org.springframework.web.bind.annotation.RequestParam;
import ua.restaurant.spring.domain.Bill;
import ua.restaurant.spring.dto.UserInfoDTO;
import ua.restaurant.spring.exceptions.IdNotFoundExeption;
import ua.restaurant.spring.service.ClientBillsService;
import ua.restaurant.spring.service.UserInfoDTOService;

@Slf4j
@Controller
@RequestMapping( "/admin/stats/client" )
public class AdminClientStatsController {
    private final static String ADMIN_CLIENT_STATS_PAGE = "adminclientstats";
    private final static String ADMIN_CLIENT_STATS_ERROR_REDIRECT = "redirect:/admin/stats?idnotfound";
    private ClientBillsService clientBillsService;
    private UserInfoDTOService userInfoDTOService;

    @Autowired
    public AdminClientStatsController(ClientBillsService clientBillsService
            , UserInfoDTOService userInfoDTOService) {
        this.clientBillsService = clientBillsService;
        this.userInfoDTOService = userInfoDTOService;
    }

    @GetMapping
    @PreAuthorize( "hasAuthority('ADMIN')" )
    public String getClientStatsPage(@PageableDefault( size = 5 ) Pageable pageable,
                                     @RequestParam( name = "id" ) Long id,
                                     Model model) {
        UserInfoDTO userInfoDTO = null;
        try {
            userInfoDTO = userInfoDTOService
                    .getUserInfDTOById(id);
        } catch (IdNotFoundExeption e) {
            return handleIdNotFoundExc(e);
        }

        Page<Bill> page = clientBillsService
                .getBillsByUserIdNewestFirst(id, pageable);

        model.addAttribute(userInfoDTO);
        model.addAttribute("page", page);
        return ADMIN_CLIENT_STATS_PAGE;
    }

    private String handleIdNotFoundExc(IdNotFoundExeption e) {
        log.warn(e.getMessage() + e.getId());
        return ADMIN_CLIENT_STATS_ERROR_REDIRECT;
    }

}
