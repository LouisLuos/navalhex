package br.com.navalhex.modules.tenants.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.navalhex.modules.tenants.dto.RegisterDTO;
import br.com.navalhex.modules.tenants.service.TenantService;
import br.com.navalhex.modules.user.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import br.com.navalhex.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tenants")
public class TenantController {
    private final TenantService tenantService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> register(
        @Valid @RequestBody RegisterDTO dtoRegisterTenant,
        @AuthenticationPrincipal UserEntity user) {
        this.tenantService.reigsterTenant(dtoRegisterTenant, user);
        return ApiResponse.success(HttpStatus.CREATED, "Tenant registered successfully", null);
    }
}
