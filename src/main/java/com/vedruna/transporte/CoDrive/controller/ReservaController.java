package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.CrearReservaRequest;
import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.ViajeRepository;
import com.vedruna.transporte.CoDrive.services.ReservaServiceI;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaServiceI reservaService;
    private final UsuarioRepository usuarioRepository;
    private final ViajeRepository viajeRepository;

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody CrearReservaRequest request) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar usuario completo en BD por email
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar viaje en BD por id
        Viaje viaje = viajeRepository.findById(request.getViajeId())
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Crear reserva y asignar datos
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setViaje(viaje);
        reserva.setEstado("PENDIENTE");
        reserva.setFechaReserva(new Date());

        Reserva creada = reservaService.crearReserva(reserva);
        return ResponseEntity.ok(creada);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@Valid @PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva actualizada = reservaService.actualizarReserva(id, reserva);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.obtenerReservasPorUsuario(usuarioId));
    }

    @GetMapping("/mis-viajes/reservas")
    public ResponseEntity<List<Reserva>> obtenerMisReservasComoConductor() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario conductor = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Reserva> reservas = reservaService.obtenerReservasPorConductor(conductor.getId());
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/viaje/{viajeId}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(reservaService.obtenerReservasPorViaje(viajeId));
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarReserva(@PathVariable Long id) {
        reservaService.confirmarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
