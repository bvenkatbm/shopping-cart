package com.maersk.shoppingcart.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class CentralExceptionHandler {

    @ExceptionHandler(value = {NotAValidProductException.class,
            OutOfQuantityException.class, ItemNotFoundInCartException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBadRequest(HttpServletRequest request, Exception exception) {
        return problemDetails(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetails handleInternalServerError(HttpServletRequest request, Exception exception) {
        log.error("Error: ", exception);
        return problemDetails(request, "Something went wrong, Please try later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ProblemDetails handleAccessDeniedException(
            AccessDeniedException exception, HttpServletRequest request, HttpServletResponse response) {
        return problemDetails(request, "Access is Forbidden on the requested resource", HttpStatus.FORBIDDEN);
    }

    private ProblemDetails problemDetails(HttpServletRequest request, String message, HttpStatus status) {
        ProblemDetails details = new ProblemDetails();
        details.setErrorCode(status.value());
        details.setMessage(message);
        details.setPath(request.getRequestURI());
        details.setTime(LocalDateTime.now());
        return details;
    }
}
