package br.com.navalhex.modules.treatments.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "treatments")
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    @NotBlank
    @Size(min = 3, max = 30, message = "O título deve conter entre 3 e 30 caracteres")
    private String title;

    @Column(name = "description")
    @Size(min = 10, max = 255, message = "A descrição deve conter entre 10 e 255 caracteres")
    private String description;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "duration_minutes")
    @NotNull
    private Integer duration;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "tenant_id")
    @NotNull
    private UUID tenantId;
}
