package br.com.navalhex.modules.tenants.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.com.navalhex.modules.tenants.entity.TenantEntity;

@Service
public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {
    
    Boolean existsBySlug(String slug);
    Boolean existsByOwnerId(UUID ownerId);
    Optional<TenantEntity> findBySlug(String slug);

    
}
