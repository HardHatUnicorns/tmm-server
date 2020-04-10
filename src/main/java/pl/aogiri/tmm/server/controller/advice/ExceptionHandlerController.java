package pl.aogiri.tmm.server.controller.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.aogiri.tmm.server.exception.api.ApiException;
import pl.aogiri.tmm.server.exception.api.register.EmailExistException;
import pl.aogiri.tmm.server.exception.api.register.FieldRequiredException;
import pl.aogiri.tmm.server.exception.api.register.LoginExistException;
import pl.aogiri.tmm.server.exception.api.register.RegisterException;
import pl.aogiri.tmm.server.exception.api.token.ActivationFailedException;
import pl.aogiri.tmm.server.response.error.ErrorResponse;
import pl.aogiri.tmm.server.response.error.sub.SubError;
import pl.aogiri.tmm.server.response.error.sub.ValidationSubError;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(1)
public class ExceptionHandlerController {

    @ExceptionHandler(value = {
            LoginExistException.class,
            EmailExistException.class
    })
    public ResponseEntity<ErrorResponse> conflictException(RegisterException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT,
                Collections.singleton(
                        new ValidationSubError(
                                ex.getField(),
                                ex.getMessage()
                                )
                )),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {
            FieldRequiredException.class
    })
    public ResponseEntity<ErrorResponse> unprocessableEntityException(RegisterException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                Collections.singleton(
                        new ValidationSubError(
                                ex.getField(),
                                ex.getMessage()
                        )
                )),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {
            ActivationFailedException.class
    })
    public ResponseEntity<ErrorResponse> notExistException(ApiException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NO_CONTENT,
                Collections.singleton(
                        new ValidationSubError(
                                ex.getMessage()
                        )
                )),
                HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> unprocessableEntityException(MethodArgumentNotValidException ex){
        List<SubError> errorList = ex.getBindingResult().getFieldErrors().parallelStream().map(error -> new ValidationSubError(error.getObjectName(), error.getField(), error.getRejectedValue(), error.getDefaultMessage())).collect(Collectors.toCollection(LinkedList::new));
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, errorList), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
