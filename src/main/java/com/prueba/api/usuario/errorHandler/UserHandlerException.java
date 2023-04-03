package com.prueba.api.usuario.errorHandler;

import com.prueba.api.usuario.model.UserExceptionModel;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
@Order(Ordered.LOWEST_PRECEDENCE - 2)
@ControllerAdvice
public class UserHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserExceptionModel> handleCustomException(UserException ex){
        UserExceptionModel globalExceptionModel = new UserExceptionModel();
        globalExceptionModel.setMensaje(ex.getMessage());
        return new ResponseEntity<>(globalExceptionModel, ex.getHttpStatus());
    }

    /**
     * Error Handler anotacion @Valid. Sobrescribe el error generico de MethodArgumentNotValidException  y
     * retorna un arreglo de los compos con error
     * @param ex MethodArgumentNotValidException
     * @param headers HttpHeaders
     * @param status status
     * @param request request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField().concat(": ").concat(Objects.requireNonNull(x.getDefaultMessage())))
                .collect(Collectors.toList());
        body.put("mensaje", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    protected Object handleDalServiceException(RuntimeException ex) {
        UserExceptionModel globalExceptionModel = new UserExceptionModel();
        globalExceptionModel.setMensaje(ex.getMessage());
        return globalExceptionModel;
    }
}
