package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.CrearViajeRequest;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.services.ViajeServiceI;
import com.vedruna.transporte.CoDrive.exceptions.NoEsConductorException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viajes")
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeServiceI viajeService;

    // Crear nuevo viaje
    @PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody CrearViajeRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userDetails.getAuthorities().toString().contains("ROLE_CONDUCTOR")) {
            throw new NoEsConductorException("Solo los conductores pueden crear viajes");
        }

        Viaje viaje = new Viaje();
        viaje.setOrigen(request.getOrigen());
        viaje.setDestino(request.getDestino());
        viaje.setFechaHoraSalida(request.getFechaHoraSalida());
        viaje.setFechaHoraLlegada(request.getFechaHoraLlegada());
        viaje.setPlazasTotales(request.getPlazasTotales());
        viaje.setPlazasDisponibles(request.getPlazasTotales());

        Usuario conductor = new Usuario();
        conductor.setEmail(userDetails.getUsername());
        viaje.setConductor(conductor);

        return ResponseEntity.ok(viajeService.crearViaje(viaje));
    }

    // Obtener viajes con plazas disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Viaje>> verDisponibles(
            @RequestParam(required = false) String origen,
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) Integer plazasMin
    ) {
        return ResponseEntity.ok(viajeService.buscarPorFiltros(origen, destino, plazasMin));
    }

    // Obtener los viajes del usuario logueado
    @GetMapping("/mis-viajes")
    public ResponseEntity<List<Viaje>> verMisViajes() {
        return ResponseEntity.ok(viajeService.buscarMisViajes());
    }

    // 🚫 Cancelar un viaje (solo conductor)
    @DeleteMapping("/{viajeId}")
    public ResponseEntity<Void> cancelarViaje(@PathVariable Long viajeId) {
        viajeService.cancelarViaje(viajeId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Unirse a un viaje como pasajero
    @PostMapping("/{viajeId}/unirse")
    public ResponseEntity<Viaje> unirseAViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(viajeService.unirseAViaje(viajeId));
    }

    // ↩️ Abandonar un viaje
    @PostMapping("/{viajeId}/abandonar")
    public ResponseEntity<Viaje> abandonarViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(viajeService.abandonarViaje(viajeId));
    }

    // 👥 Obtener pasajeros de un viaje
    @GetMapping("/{viajeId}/pasajeros")
    public ResponseEntity<List<Usuario>> obtenerPasajeros(@PathVariable Long viajeId) {
        return ResponseEntity.ok(viajeService.obtenerPasajerosDeViaje(viajeId));
    }
}
