package br.com.navalhex.modules.user.dto;

import br.com.navalhex.modules.user.entity.UserEntity;
import br.com.navalhex.modules.user.entity.UserRole;

public record LoginResponseDTO(String token, String name, String email, UserRole role) {
    public LoginResponseDTO(UserEntity user, String token) {
        this(token, user.getName(), user.getEmail(), user.getRole());
    }
}
