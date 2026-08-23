package br.com.navalhex.modules.user.controller;

import br.com.navalhex.modules.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.navalhex.modules.user.dto.LoginDTO;
import br.com.navalhex.modules.user.dto.LoginResponseDTO;
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

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(loginDTO);
        return ApiResponse.success(HttpStatus.OK, "Login realizado com sucesso!", loginResponseDTO);
    }
}
