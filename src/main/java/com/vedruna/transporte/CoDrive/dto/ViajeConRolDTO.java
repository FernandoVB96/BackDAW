package com.vedruna.transporte.CoDrive.dto;

import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ViajeConRolDTO {
    private Long id;
    private String origen;
    private String destino;
    private LocalDateTime fechaHoraSalida;
    private int plazasDisponibles;
    private Long conductor_id;
    private String rolUsuario;

    public ViajeConRolDTO(Viaje viaje, String rolUsuario) {
        this.id = viaje.getId();
        this.origen = viaje.getOrigen();
        this.destino = viaje.getDestino();
        this.fechaHoraSalida = viaje.getFechaHoraSalida();
        this.plazasDisponibles = viaje.getPlazasDisponibles();
        this.conductor_id = viaje.getConductor().getId();
        this.rolUsuario = rolUsuario;
    }
}
