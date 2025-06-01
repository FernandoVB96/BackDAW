package com.vedruna.transporte.CoDrive.dto;

import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActualizarUsuarioDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Teléfono no válido")
    private String telefono;

    @Email(message = "Email no válido")
    private String email;

    private Rol rol;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}
