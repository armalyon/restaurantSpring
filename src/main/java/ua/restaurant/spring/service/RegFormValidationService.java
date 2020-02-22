package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.dto.AccountDTO;
import ua.restaurant.spring.exceptions.ConfirmationDoesNotMatchException;
import ua.restaurant.spring.exceptions.RegexMismatchException;
import ua.restaurant.spring.service.utility.ValidationUtilityService;

@Service
public class RegFormValidationService {

    private ValidationUtilityService validationUtilityService;

    @Autowired
    public RegFormValidationService(ValidationUtilityService validationUtilityService) {
        this.validationUtilityService = validationUtilityService;
    }

    public boolean isRegFormValid(AccountDTO accountDTO)
            throws ConfirmationDoesNotMatchException, RegexMismatchException {
        return validationUtilityService
                .isPasswordCanBeUsed(accountDTO.getPassword(), accountDTO.getPasswordConfirmation())
                && validationUtilityService
                .isUsernameValid(accountDTO.getUsername())
                && validationUtilityService
                .validateNameAndSurname(accountDTO.getName(), accountDTO.getSurname());
    }
}
