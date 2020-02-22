package ua.restaurant.spring.service.utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.restaurant.spring.exceptions.ConfirmationDoesNotMatchException;
import ua.restaurant.spring.exceptions.RegexMismatchException;


@RunWith( MockitoJUnitRunner.class )
public class ValidationUtilityServiceTest {

    private static final String PASSWORD_GOOD = "password1234";
    private static final String PASSWORD_GOOD_2 = "password12345";
    private static final String PASSWORD_SHORT = "pass123";
    private static final String USERNAME_VALID = "username123";
    private static final String USERNAME_INVALID_SYMBOLS = "username#";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String NAME_INVALID = "name";
    private static final String SURNAME_INVALID = "surname";

    @InjectMocks
    private ValidationUtilityService instance;


    @Test
    public void shouldReturnTrueWhenPasswordAndConfirmationMatchAndRegexMatches()
            throws ConfirmationDoesNotMatchException, RegexMismatchException {
        boolean result = instance.isPasswordCanBeUsed(PASSWORD_GOOD, PASSWORD_GOOD);
        Assert.assertTrue(result);
    }

    @Test( expected = RegexMismatchException.class )
    public void shouldThrowRegExMisMatchExceptionWhenPasswordAndCOnfirmationDoNotMatchRegex()
            throws ConfirmationDoesNotMatchException, RegexMismatchException {
        instance.isPasswordCanBeUsed(PASSWORD_SHORT, PASSWORD_SHORT);
    }

    @Test( expected = ConfirmationDoesNotMatchException.class )
    public void shouldThrowConfirmationDoesNotMatchExceptionWhenPasswordAndCOnfirmationDoNotMatch()
            throws ConfirmationDoesNotMatchException, RegexMismatchException {
        instance.isPasswordCanBeUsed(PASSWORD_GOOD, PASSWORD_GOOD_2);
    }

    @Test
    public void shouldReturnTrueWhenUsernameIsValid() throws RegexMismatchException {
        boolean result = instance.isUsernameValid(USERNAME_VALID);
        Assert.assertTrue(result);
    }


    @Test( expected = RegexMismatchException.class )
    public void shouldThrowExceptionWhenUsernameDoesNotMatchRegex() throws RegexMismatchException {
        instance.isUsernameValid(USERNAME_INVALID_SYMBOLS);
    }

    @Test
    public void shouldReturnTrueIfNameAndSurnameValid() throws RegexMismatchException {
        boolean result = instance.validateNameAndSurname(NAME, SURNAME);
        Assert.assertTrue(result);
    }

    @Test( expected = RegexMismatchException.class )
    public void shouldThrowRegexMismatchExceptionWhenNameAndSurnameInvalid() throws RegexMismatchException {
        instance.validateNameAndSurname(NAME_INVALID, SURNAME_INVALID);
    }
}