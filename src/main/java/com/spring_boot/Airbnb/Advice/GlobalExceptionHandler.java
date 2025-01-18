package com.spring_boot.Airbnb.Advice;


import com.spring_boot.Airbnb.Exceptions.ResourceNotFoundExceptions;
import com.spring_boot.Airbnb.Exceptions.UnAuthorisedException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundExceptions resourceNotFoundExceptions){
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(resourceNotFoundExceptions.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception){
        ApiError apiError = ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(UnAuthorisedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnAuthorised(UnAuthorisedException unAuthorisedException){
        ApiError apiError = ApiError.builder().status(HttpStatus.UNAUTHORIZED).message(unAuthorisedException.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException authenticationException){
        ApiError apiError = ApiError.builder().status(HttpStatus.UNAUTHORIZED).message(authenticationException.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException jwtException){
        ApiError apiError = ApiError.builder().status(HttpStatus.FORBIDDEN).message(jwtException.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDinedException(AccessDeniedException accessDeniedException){
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(accessDeniedException.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError){
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }

}
