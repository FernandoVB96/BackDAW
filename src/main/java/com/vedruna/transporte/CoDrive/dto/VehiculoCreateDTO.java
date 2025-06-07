package com.vedruna.transporte.CoDrive.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehiculoCreateDTO {

    @NotNull(message = "Marca es obligatoria")
    @Size(min = 1, max = 50, message = "Marca debe tener entre 1 y 50 caracteres")
    private String marca;

    @NotNull(message = "Modelo es obligatorio")
    @Size(min = 1, max = 50, message = "Modelo debe tener entre 1 y 50 caracteres")
    private String modelo;

    @NotNull(message = "Matrícula es obligatoria")
    @Size(min = 1, max = 15, message = "Matrícula debe tener entre 1 y 15 caracteres")
    private String matricula;

    private int plazasDisponibles;

    @NotNull(message = "Debe especificarse el ID del conductor")
    private Long conductorId;
}
