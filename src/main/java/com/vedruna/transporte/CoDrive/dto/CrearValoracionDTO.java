package com.vedruna.transporte.CoDrive.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CrearValoracionDTO {
    @Min(value = 1, message = "La puntuación debe ser al menos 1")
    @Max(value = 5, message = "La puntuación no puede ser mayor que 5")
    private int puntuacion;

    @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotNull(message = "El ID del conductor es obligatorio")
    private Long conductorId;
}

