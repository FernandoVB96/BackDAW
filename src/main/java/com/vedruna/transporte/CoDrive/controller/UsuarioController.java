package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.services.UsuarioServiceI;
import com.vedruna.transporte.CoDrive.services.VehiculoServiceI;
import com.vedruna.transporte.CoDrive.services.ViajeServiceI;
import com.vedruna.transporte.CoDrive.services.ConductorServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceI usuarioService;
    private final VehiculoServiceI vehiculoService;
    private final ViajeServiceI viajeService;
    private final ConductorServiceI conductorService;

    // === Funciones generales de Usuario ===

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
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(@PathVariable Rol rol) {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosPorRol(rol));
    }

    // === Funcionalidades de Conductor ===

    @PostMapping("/{usuarioId}/vehiculos")
    public ResponseEntity<Void> agregarVehiculo(@PathVariable Long usuarioId, @RequestBody Vehiculo vehiculo) {
        conductorService.agregarVehiculo(usuarioId, vehiculo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}/vehiculos/{vehiculoId}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long usuarioId, @PathVariable Long vehiculoId) {
        conductorService.eliminarVehiculo(usuarioId, vehiculoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{usuarioId}/vehiculos")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(vehiculoService.obtenerVehiculosPorConductor(usuarioId));
    }

    @GetMapping("/{usuarioId}/viajes")
    public ResponseEntity<List<Viaje>> verViajesPublicados(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(viajeService.obtenerViajesPorConductor(usuarioId));
    }

    @GetMapping("/{usuarioId}/valoracion-media")
    public ResponseEntity<Double> calcularValoracionMedia(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(conductorService.calcularValoracionMedia(usuarioId));
    }
}
