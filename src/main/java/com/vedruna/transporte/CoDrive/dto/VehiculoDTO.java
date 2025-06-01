package com.vedruna.transporte.CoDrive.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehiculoDTO {
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    private String matricula;
    @Min(1)
    private int plazasDisponibles;
}
