package br.com.navalhex.modules.user.controller;

import br.com.navalhex.modules.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.navalhex.modules.user.dto.RegisterDTO;
import br.com.navalhex.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> register(@Valid @RequestBody RegisterDTO dtoRegister) {
        userService.createUser(dtoRegister);
        return ApiResponse.success(HttpStatus.CREATED, "Usuário cadastrado com sucesso!", null);
    }
}
