package br.com.navalhex.modules.treatments.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TreatmentsDTO(
        @NotBlank
        @Size(min = 3, max = 30)
        String title,

        @Size(min = 10, max = 255)
        String description,

        @NotNull
        BigDecimal price,

        @NotNull
        Integer durationMinutes) {

}
