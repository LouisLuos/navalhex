package br.com.navalhex.modules.treatments.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ResponseTreatmentsDTO(
    UUID id,
    String title,
    String description,
    BigDecimal price,
    Integer durationMinutes
) {

}
