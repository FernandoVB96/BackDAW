package com.vedruna.transporte.CoDrive.dto;

import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import lombok.Data;

@Data
public class ActualizarUsuarioDTO {
    private String nombre;
    private String telefono;
    private String email;
    private Rol rol;
    private String password;
}
