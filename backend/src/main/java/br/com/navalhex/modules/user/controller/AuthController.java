package br.com.navalhex.modules.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.navalhex.modules.user.dto.RegisterDTO;
import br.com.navalhex.utils.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterDTO> register(@Valid @RequestBody RegisterDTO dtoRegister) {
        return ApiResponse.success(HttpStatus.CREATED, "Usuário cadastrado com sucesso!", dtoRegister);
    }
}
