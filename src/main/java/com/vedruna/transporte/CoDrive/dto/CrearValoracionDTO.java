package com.vedruna.transporte.CoDrive.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class CrearValoracionDTO {
    private int puntuacion;
    private String comentario;
    private LocalDateTime fecha;
    private Long conductorId;
}

