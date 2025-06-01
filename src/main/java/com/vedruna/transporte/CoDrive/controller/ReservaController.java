package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.services.ReservaServiceI;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaServiceI reservaService;

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody Reserva reserva) {
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
