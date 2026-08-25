package br.com.navalhex.modules.treatments.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.navalhex.modules.treatments.dto.ResponseTreatmentsDTO;
import br.com.navalhex.modules.treatments.dto.TreatmentsDTO;
import br.com.navalhex.modules.treatments.entity.TreatmentsEntity;
import br.com.navalhex.modules.treatments.repository.TreatmentsRepository;
import br.com.navalhex.modules.user.entity.UserEntity;
import br.com.navalhex.modules.tenants.entity.TenantEntity;
import br.com.navalhex.modules.tenants.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TreatmentsService {

    private final TenantRepository tenantRepository;
    private final TreatmentsRepository treatmentsRepository;

    @Transactional
    public void createTreatment(UserEntity user, String slug, TreatmentsDTO treatmentsDTO) {
        
        TenantEntity tenant = this.tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Barbearia não encontrada"));

        if (!Objects.equals(user.getId(), tenant.getOwnerId())) {
            throw new IllegalArgumentException("Você não tem permissão para criar um tratamento");
        }

        TreatmentsEntity treatment = TreatmentsEntity.builder()
                .title(treatmentsDTO.title())
                .description(treatmentsDTO.description())
                .price(treatmentsDTO.price())
                .duration(treatmentsDTO.durationMinutes())
                .tenantId(tenant.getId())
                .build();
        
        this.treatmentsRepository.save(treatment);
        
    }

    public List<ResponseTreatmentsDTO> getTreatments (String slug) {
        TenantEntity tenant = this.tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Barbearia não encontrada"));

        return this.treatmentsRepository.findByTenantId(tenant.getId())
                .stream()
                .map(treatment -> new ResponseTreatmentsDTO(
                    treatment.getId(),
                    treatment.getTitle(),
                    treatment.getDescription(),
                    treatment.getPrice(),
                    treatment.getDuration()
                ))
                .collect(Collectors.toList());
    }
}
