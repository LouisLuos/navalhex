package br.com.navalhex.modules.user.dto;

import br.com.navalhex.modules.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
    String name,
    
    @NotBlank(message = "Email is required")
    @Email(message = "O email deve ser válido")
    String email,
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 60, message = "A senha deve conter entre 8 e 60 caracteres, pelo menos um minúsuclo um maiúsculo e um caractere especial")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "Sua senha deve conter pelo menos 8 caracteres, incluindo 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial (ex: @, #, $).")
    String password,

    @NotNull(message = "Role is required")
    UserRole role,
    
    @NotBlank(message = "Whatsapp is required")
    @Size(min = 11, max = 15, message = "O whatsapp deve conter entre 11 e 15 caracteres")
    @Pattern(regexp = "^(?:\\+?55\\s?)?(?:\\([1-9]{2}\\)|[1-9]{2})\\s?9?[0-9]{4}-?[0-9]{4}$", message = "Digite um número de WhatsApp válido com o DDD. Exemplo: (11) 91234-5678.")
    String whatsapp
) {}
