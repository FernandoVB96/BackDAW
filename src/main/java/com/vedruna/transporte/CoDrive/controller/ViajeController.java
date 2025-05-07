package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.CrearViajeRequest;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.services.ViajeServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viajes")
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeServiceI viajeService;

    // Modificado: creando un viaje desde el request DTO
    @PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody CrearViajeRequest request) {
        // Convertir el request a un modelo Viaje
        Viaje viaje = new Viaje();
        viaje.setOrigen(request.getOrigen());
        viaje.setDestino(request.getDestino());
        viaje.setFechaHoraSalida(request.getFechaHoraSalida());
        viaje.setFechaHoraLlegada(request.getFechaHoraLlegada());
        viaje.setPlazasTotales(request.getPlazasTotales());
        viaje.setPlazasDisponibles(request.getPlazasTotales()); // Suponiendo que las plazas disponibles sean inicialmente las mismas que las totales

        Viaje nuevoViaje = viajeService.crearViaje(viaje);
        return ResponseEntity.ok(nuevoViaje);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Viaje>> verDisponibles() {
        List<Viaje> viajesDisponibles = viajeService.buscarPorPlazasDisponibles();
        return ResponseEntity.ok(viajesDisponibles);
    }

    @GetMapping("/mis-viajes")
    public ResponseEntity<List<Viaje>> verMisViajes() {
        // Suponiendo que el método en el servicio obtenga los viajes del usuario logueado
        List<Viaje> misViajes = viajeService.buscarMisViajes();
        return ResponseEntity.ok(misViajes);
    }
}
