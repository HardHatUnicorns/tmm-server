package pl.aogiri.tmm.server.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.aogiri.tmm.server.controller.implementation.UserController;
import pl.aogiri.tmm.server.exception.api.register.EmailExistException;
import pl.aogiri.tmm.server.exception.api.register.FieldRequiredException;
import pl.aogiri.tmm.server.exception.api.register.LoginExistException;
import pl.aogiri.tmm.server.exception.api.register.RegisterException;
import pl.aogiri.tmm.server.response.error.ErrorResponse;

@ControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandlerController {

    @ExceptionHandler(value = {
            LoginExistException.class,
            EmailExistException.class
    })
    public ResponseEntity<ErrorResponse> conflictException(RegisterException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {
            FieldRequiredException.class
    })
    public ResponseEntity<ErrorResponse> unprocessableEntityException(RegisterException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }



}
