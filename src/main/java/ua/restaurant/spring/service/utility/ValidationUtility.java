package ua.restaurant.spring.service.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ua.restaurant.spring.exceptions.ConfirmationDoesNotMatchException;
import ua.restaurant.spring.exceptions.RegexMismatchException;

import java.util.Objects;
import java.util.regex.Pattern;

import static ua.restaurant.spring.service.utility.Constants.*;

@Slf4j
@Service
@PropertySource( value = "classpath:additionalVariables.properties", encoding = "UTF-8" )
public class ValidationUtility {
    private Environment environment;

    @Autowired
    public ValidationUtility(Environment environment) {
        this.environment = environment;
    }

    public boolean isPasswordCanBeUsed(String password,
                                       String confirmation
    ) throws ConfirmationDoesNotMatchException, RegexMismatchException {
        if (!password.equals(confirmation)) throw new ConfirmationDoesNotMatchException();

        if (!getPattern(PASSWORD_REGEX).matcher(password).matches()) {
            throw new RegexMismatchException(PASSWORD_FIELD);
        }
        return true;
    }

    public boolean isUsernameValid(String username) throws RegexMismatchException {
        if (!getPattern(USERNAME_REGEX).matcher(username).matches()) {
            throw new RegexMismatchException(USERNAME_FIELD);
        }
        return true;
    }

    public boolean validateNameAndSurname(String name, String surname) throws RegexMismatchException {
        Pattern pattern = getPattern(NAME_REGEX);
        if (!pattern.matcher(name).matches() ||
                !pattern.matcher(surname).matches()) throw new RegexMismatchException(NAME_FIELD);
        return true;
    }

    private Pattern getPattern(String regexContainer) {
        return Pattern.compile(Objects.requireNonNull(
                environment
                        .getProperty(regexContainer))
        );
    }
}
