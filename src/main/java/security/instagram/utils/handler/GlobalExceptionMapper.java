package security.instagram.utils.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;
import security.instagram.utils.Constants;
import security.instagram.utils.exception.ErrorMessage;
import security.instagram.utils.exception.ForumException;
import security.instagram.utils.exception.InvalidRequestException;

import java.util.Date;

/**
 * @author ket_ein17
 * @Date 8/31/2023
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionMapper extends ResponseEntityExceptionHandler {
    //InvocationTargetException
    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleBusiness(AuthenticationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> notFoundException(NotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = {ForumException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleMethodArgument(ForumException ex, WebRequest request) {
        log.info(Constants.SEPARATE);
        log.error("A Forum Error has been throw!");
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.info(Constants.SEPARATE);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> invalidRequest(InvalidRequestException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> serverErrorException(HttpServerErrorException.InternalServerError ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
