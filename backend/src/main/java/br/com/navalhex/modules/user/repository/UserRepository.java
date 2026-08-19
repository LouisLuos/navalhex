package br.com.navalhex.modules.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navalhex.modules.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByWhatsapp(String whatsapp);
    boolean existsByEmail(String email);
    boolean existsByWhatsapp(String whatsapp);

}
