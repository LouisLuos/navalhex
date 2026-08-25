package br.com.navalhex.modules.treatments.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.navalhex.modules.treatments.dto.ResponseTreatmentsDTO;
import br.com.navalhex.modules.treatments.dto.TreatmentsDTO;
import br.com.navalhex.modules.treatments.service.TreatmentsService;
import br.com.navalhex.modules.user.entity.UserEntity;
import br.com.navalhex.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tenants/{slug}/treatments")
public class TreatmentsController {

    private final TreatmentsService treatmentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> create(@AuthenticationPrincipal UserEntity user, @PathVariable String slug, @Valid @RequestBody TreatmentsDTO treatmentsDTO) {
        this.treatmentsService.createTreatment(user, slug, treatmentsDTO);
        return ApiResponse.success(HttpStatus.CREATED, "Serviço criado com sucesso", null);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ResponseTreatmentsDTO>> getTreatments(@PathVariable String slug) {
        var treatments = this.treatmentsService.getTreatments(slug);
        return ApiResponse.success(HttpStatus.OK, "Serviço encontrado com sucesso", treatments);
    }

}
