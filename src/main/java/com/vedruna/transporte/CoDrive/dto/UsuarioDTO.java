package com.vedruna.transporte.CoDrive.dto;

import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Rol rol;
}
