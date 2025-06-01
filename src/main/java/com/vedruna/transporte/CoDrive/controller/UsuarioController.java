package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.ActualizarUsuarioDTO;
import com.vedruna.transporte.CoDrive.dto.VehiculoDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.services.UsuarioServiceI;
import com.vedruna.transporte.CoDrive.services.VehiculoServiceI;
import com.vedruna.transporte.CoDrive.services.ViajeServiceI;
import com.vedruna.transporte.CoDrive.services.ConductorServiceI;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Validated
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

    // Aquí decimos que valide el DTO con @Valid para que se active la validación de campos
    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarPerfil(@Valid @RequestBody ActualizarUsuarioDTO datosActualizados) {
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

    // Validamos que el email no sea vacío y tenga formato válido con @NotBlank y @Email
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorEmail(
            @RequestParam @NotBlank(message = "El email es obligatorio") @Email(message = "Email inválido") String email) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Validamos que el rol sea uno válido
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorRol(@PathVariable Rol rol) {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosPorRol(rol));
    }

    // === Funcionalidades de Conductor ===

    @PostMapping("/{usuarioId}/vehiculos")
    public ResponseEntity<Void> agregarVehiculo(@PathVariable Long usuarioId, @Valid @RequestBody VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = Vehiculo.builder()
                .marca(vehiculoDTO.getMarca())
                .modelo(vehiculoDTO.getModelo())
                .matricula(vehiculoDTO.getMatricula())
                .plazasDisponibles(vehiculoDTO.getPlazasDisponibles())
                .build();

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
