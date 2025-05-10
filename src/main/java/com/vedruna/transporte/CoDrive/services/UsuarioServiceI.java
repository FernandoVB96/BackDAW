package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Rol;

import java.util.List;
import java.util.Optional;

public interface UsuarioServiceI {
    Usuario obtenerMiPerfil();
    Usuario actualizarPerfil(Usuario datosActualizados);
    void eliminarMiCuenta();
    List<Usuario> obtenerTodos();
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> obtenerUsuariosPorRol(Rol rol);
}
