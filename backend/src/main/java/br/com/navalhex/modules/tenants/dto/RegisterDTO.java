package br.com.navalhex.modules.tenants.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(

    @NotBlank
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
    String companyName,

    @NotNull
    LocalTime openingHours,

    @NotNull
    LocalTime closingHours,

    @NotBlank
    @Size(min = 3, max = 100, message = "O slug deve conter entre 3 e 100 caracteres")
    String slug,

    @NotBlank
    @Size(min = 11, max = 15, message = "O whatsapp deve conter entre 11 e 15 caracteres")
    @Pattern(regexp = "^(?:\\+?55\\s?)?(?:\\([1-9]{2}\\)|[1-9]{2})\\s?9?[0-9]{4}-?[0-9]{4}$", message = "Digite um número de WhatsApp válido com o DDD. Exemplo: (11) 91234-5678.")
    String whatsapp,

    @NotBlank
    @Size(min = 3, max = 255, message = "O endereço deve conter entre 3 e 255 caracteres")
    String companyAddress
) {

}
