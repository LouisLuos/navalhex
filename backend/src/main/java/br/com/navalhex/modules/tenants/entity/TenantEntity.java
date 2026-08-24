package br.com.navalhex.modules.tenants.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "tenants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "company_name")
    @NotBlank
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
    private String companyName;

    @Column(name = "opening_hours")
    @NotNull
    private LocalTime openingHours;

    @Column(name = "closing_hours")
    @NotNull
    private LocalTime closingHours;

    @NotBlank
    @Size(min = 11, max = 15, message = "O whatsapp deve conter entre 11 e 15 caracteres")
    @Pattern(regexp = "^(?:\\+?55\\s?)?(?:\\([1-9]{2}\\)|[1-9]{2})\\s?9?[0-9]{4}-?[0-9]{4}$", message = "Digite um número de WhatsApp válido com o DDD. Exemplo: (11) 91234-5678.")
    private String whatsapp;

    @NotBlank
    @Size(min = 3, max = 255, message = "O endereço deve conter entre 3 e 255 caracteres")
    private String companyAddress;

    @Column(unique = true)
    @NotBlank
    @Size(min = 3, max = 100, message = "O slug deve conter entre 3 e 100 caracteres")
    private String slug;

    @Column(name = "owner_id", nullable = false, unique = true)
    private UUID ownerId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true, nullable = false)
    private LocalDateTime updatedAt;

}
