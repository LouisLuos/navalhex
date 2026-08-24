package br.com.navalhex.modules.tenants.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.navalhex.modules.tenants.dto.RegisterDTO;
import br.com.navalhex.modules.tenants.dto.TenantResponseDTO;
import br.com.navalhex.modules.tenants.entity.TenantEntity;
import br.com.navalhex.modules.tenants.repository.TenantRepository;
import br.com.navalhex.modules.user.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;

    public void reigsterTenant(@Valid RegisterDTO dtoRegisterTenant, UserEntity user) {
        if (tenantRepository.existsBySlug(dtoRegisterTenant.slug())) {
            throw new IllegalArgumentException("Slug já cadastrado");
        }

        if (tenantRepository.existsByOwnerId(user.getId())) {
    throw new IllegalArgumentException("Usuário já possui uma barbearia cadastrada");
}

        TenantEntity tenant = new TenantEntity();
        tenant.setCompanyName(dtoRegisterTenant.companyName());
        tenant.setOpeningHours(dtoRegisterTenant.openingHours());
        tenant.setClosingHours(dtoRegisterTenant.closingHours());
        tenant.setWhatsapp(dtoRegisterTenant.whatsapp());
        tenant.setCompanyAddress(dtoRegisterTenant.companyAddress());
        tenant.setSlug(dtoRegisterTenant.slug());
        tenant.setOwnerId(user.getId());
        tenantRepository.save(tenant);
    }


    public TenantResponseDTO getTenantBySlug(String slug) {
    TenantEntity tenant = tenantRepository.findBySlug(slug)
        .orElseThrow(() -> new IllegalArgumentException("Barbearia não encontrada"));
    
    return new TenantResponseDTO(tenant);
}

}
