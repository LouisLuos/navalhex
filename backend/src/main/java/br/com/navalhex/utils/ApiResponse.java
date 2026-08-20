package br.com.navalhex.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ApiResponse<T> {
    private Integer status;
    private String message;
    private T data;
    private Map<String, String> errors;
    
    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
    public ApiResponse(HttpStatus status, String message, Map<String, String> errors) {
        this.status = status.value();
        this.message = message;
        this.errors = errors;
    }

    public static <T> ApiResponse<T> success(HttpStatus status,String message, T data) {
        return new ApiResponse<>(status, message, data);
    }
    public static <T> ApiResponse<T> error(HttpStatus status, String message, Map<String, String> errors) {
        return new ApiResponse<>(status, message, errors);
    }
}
