package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import com.vedruna.transporte.CoDrive.services.UsuarioServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceI usuarioService;

    @GetMapping("/mi-perfil")
    public ResponseEntity<Usuario> obtenerMiPerfil() {
        return ResponseEntity.ok(usuarioService.obtenerMiPerfil());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarPerfil(@RequestBody Usuario datosActualizados) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(datosActualizados));
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarMiCuenta() {
        usuarioService.eliminarMiCuenta();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(@PathVariable Rol rol) {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosPorRol(rol));
    }
}
