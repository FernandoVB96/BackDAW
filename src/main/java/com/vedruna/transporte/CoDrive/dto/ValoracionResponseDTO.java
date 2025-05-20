// ValoracionResponseDTO.java
package com.vedruna.transporte.CoDrive.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ValoracionResponseDTO {
    private Long id;
    private int puntuacion;
    private String comentario;
    private LocalDateTime fecha;
    private UsuarioDTO autor;
    private UsuarioDTO conductor;
}
