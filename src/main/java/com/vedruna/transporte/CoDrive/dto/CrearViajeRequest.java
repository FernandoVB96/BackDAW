package com.vedruna.transporte.CoDrive.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CrearViajeRequest {
    private String origen;
    private String destino;
    private LocalDateTime fechaHoraSalida;
    private LocalDateTime fechaHoraLlegada;
    private int plazasTotales;
}
