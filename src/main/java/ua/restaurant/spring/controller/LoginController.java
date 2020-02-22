package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.restaurant.spring.domain.type.Role;
import ua.restaurant.spring.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
public class LoginController {
    private final static String USER_REDIRECT = "redirect:/user";
    private final static String ADMIN_REDIRECT = "redirect:/admin";
    private final static String LOGIN_PAGE = "login";
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping( "/" )
    public String getMainPage(Principal principal) {
        Role role = userService
                .getUserRoleByUsername(principal.getName());
        switch (role) {
            case CLIENT:
                return USER_REDIRECT;
            case ADMIN:
                return ADMIN_REDIRECT;
        }
        return USER_REDIRECT;
    }


    @RequestMapping( "/login" )
    public String getLogin(@RequestParam( value = "error", required = false ) String error,
                           @RequestParam( value = "logout", required = false ) String logout,
                           Model model, HttpServletRequest request, Principal principal) {
        logoutIfLoggedIn(request, principal);
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return LOGIN_PAGE;
    }

    private void logoutIfLoggedIn(HttpServletRequest request, Principal principal) {
        if (principal != null)
            try {
                request.logout();
            } catch (ServletException e) {
                log.warn(e.getMessage());
            }
    }
}
