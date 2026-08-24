package br.com.navalhex.modules.tenants.dto;

import java.time.LocalTime;

import br.com.navalhex.modules.tenants.entity.TenantEntity;

public record TenantResponseDTO(
    String companyName,
    String slug,
    LocalTime openingHours,
    LocalTime closingHours,
    String whatsapp,
    String companyAddress
) {
    // Construtor auxiliar de conveniência recebendo a Entity
    public TenantResponseDTO(TenantEntity tenant) {
        this(
            tenant.getCompanyName(),
            tenant.getSlug(),
            tenant.getOpeningHours(),
            tenant.getClosingHours(),
            tenant.getWhatsapp(),
            tenant.getCompanyAddress()
        );
    }
}
