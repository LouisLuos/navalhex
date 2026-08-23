package br.com.navalhex.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "O email deve ser válido")
    String email,

    @NotBlank(message = "Senha é obrigatória")
    String password
) {

}
