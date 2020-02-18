package ua.restaurant.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.restaurant.spring.dto.AccountDTO;
import ua.restaurant.spring.exceptions.ConfirmationDoesNotMatchException;
import ua.restaurant.spring.exceptions.RegexMismatchException;
import ua.restaurant.spring.exceptions.UserExistsException;
import ua.restaurant.spring.service.RegFormValidationService;
import ua.restaurant.spring.service.UserRegistrationService;

import javax.validation.Valid;

import static ua.restaurant.spring.service.utility.Constants.*;

@Slf4j
@Controller
@RequestMapping( "/registration" )
public class RegistrationController {
    private final static String REGFORM_PAGE = "regform";
    private final static String LOGIN_PAGE = "login";

    private UserRegistrationService registrationService;
    private RegFormValidationService regFormValidationService;

    @Autowired
    public RegistrationController(UserRegistrationService registrationService,
                                  RegFormValidationService regFormValidationService) {
        this.registrationService = registrationService;
        this.regFormValidationService = regFormValidationService;
    }

    @GetMapping
    public String getRegistrationForm(Model model) {
        AccountDTO accountDTO = new AccountDTO();
        model.addAttribute("user", accountDTO);
        return REGFORM_PAGE;
    }

    @PostMapping
    public String registrer(@ModelAttribute( "user" ) @Valid AccountDTO accountDto,
                            BindingResult result, Model model) {
        model.addAttribute("user", accountDto);
        return createUserAccountAndReturnPage(accountDto, result);
    }


    private String createUserAccountAndReturnPage(AccountDTO accountDto, BindingResult result) {
        try {
            regFormValidationService.isRegFormValid(accountDto);
            registrationService.saveNewUser(accountDto);
        } catch (UserExistsException e) {
            result.rejectValue(USERNAME_FIELD, LOGIN_EXISTS);
            return REGFORM_PAGE;
        } catch (ConfirmationDoesNotMatchException e) {
            result.rejectValue(CONFIRMATION_FIELD, CONFIRMATION_PASSWORD_ERROR);
            return REGFORM_PAGE;
        } catch (RegexMismatchException e) {
            result.rejectValue(e.getField(), getProperValidationMessage(e.getField()));
            return REGFORM_PAGE;
        }
        return LOGIN_PAGE;
    }


    private String getProperValidationMessage(String field) {
        switch (field) {
            case PASSWORD_FIELD:
                return PASSWORD_REGEX_ERROR;
            case USERNAME_FIELD:
                return USERNAME_REGEX_ERROR;
            case NAME_FIELD:
                return NAME_REGEX_ERROR;
            default:
                return ERROR;
        }
    }

}
