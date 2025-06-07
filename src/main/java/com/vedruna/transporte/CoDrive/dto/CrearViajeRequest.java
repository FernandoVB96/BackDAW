package com.vedruna.transporte.CoDrive.dto;

import lombok.Data;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Data
public class CrearViajeRequest {

    @NotBlank(message = "El origen no puede estar vacío")
    private String origen;

    @NotBlank(message = "El destino no puede estar vacío")
    private String destino;

    @NotNull(message = "La fecha y hora de salida es obligatoria")
    @Future(message = "La fecha y hora de salida debe ser en el futuro")
    private LocalDateTime fechaHoraSalida;

    @NotNull(message = "La fecha y hora de llegada es obligatoria")
    @Future(message = "La fecha y hora de llegada debe ser en el futuro")
    private LocalDateTime fechaHoraLlegada;

    @Min(value = 1, message = "Debe haber al menos una plaza disponible")
    private int plazasTotales;
}
