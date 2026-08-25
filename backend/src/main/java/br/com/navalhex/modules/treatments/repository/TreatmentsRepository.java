package br.com.navalhex.modules.treatments.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navalhex.modules.treatments.entity.TreatmentsEntity;

public interface TreatmentsRepository extends JpaRepository<TreatmentsEntity, UUID> {
    public List<TreatmentsEntity> findByTenantId(UUID tenantId);
}
