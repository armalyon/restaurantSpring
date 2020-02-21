package ua.restaurant.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.dto.AccountDTO;
import ua.restaurant.spring.exceptions.ConfirmationDoesNotMatchException;
import ua.restaurant.spring.exceptions.RegexMismatchException;
import ua.restaurant.spring.service.utility.ValidationUtility;

@Service
public class RegFormValidationService {

    private ValidationUtility validationUtility;

    @Autowired
    public RegFormValidationService(ValidationUtility validationUtility) {
        this.validationUtility = validationUtility;
    }

    public boolean isRegFormValid(AccountDTO accountDTO)
            throws ConfirmationDoesNotMatchException, RegexMismatchException {
        return validationUtility
                .isPasswordCanBeUsed(accountDTO.getPassword(), accountDTO.getPasswordConfirmation())
                && validationUtility
                .isUsernameValid(accountDTO.getUsername())
                && validationUtility
                .validateNameAndSurname(accountDTO.getName(), accountDTO.getSurname());
    }
}
