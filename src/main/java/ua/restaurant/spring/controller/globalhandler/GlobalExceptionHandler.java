package ua.restaurant.spring.controller.globalhandler;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String INTERNAL_ERROR_PAGE = "errors/500";
    private static final String METHOD_NOT_ALLOWED_PAGE = "errors/405";
    private static final String ACCESS_DENIED_PAGE = "errors/denied";

    @ExceptionHandler( JDBCConnectionException.class )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public String handleJDBConnectionExc(JDBCConnectionException e, Model model) {
        log.warn("Exception during exution", e);
        String errorMessage = (e != null ? e.getMessage() : "Unknown error");
        model.addAttribute(errorMessage);
        return INTERNAL_ERROR_PAGE;
    }

    @ExceptionHandler( HttpRequestMethodNotSupportedException.class )
    @ResponseStatus( code = HttpStatus.METHOD_NOT_ALLOWED )
    public String handleMethodNotSupportedExc() {
        return METHOD_NOT_ALLOWED_PAGE;
    }


    @ExceptionHandler( AccessDeniedException.class )
    public String handleAccessDeniedExc(HttpServletRequest request, Principal principal) {
        log.warn(principal.getName() + " violation attempt!");
        try {
            request.logout();
        } catch (ServletException e) {
            log.warn(e.getMessage());
        }
        return ACCESS_DENIED_PAGE;
    }

    @ExceptionHandler( UsernameNotFoundException.class )
    @ResponseStatus( code = HttpStatus.NOT_FOUND )
    public String handleUsernameNotFoundExc() {
        return INTERNAL_ERROR_PAGE;
    }


}
