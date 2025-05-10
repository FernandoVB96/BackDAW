package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.persistance.models.Conductor;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.services.ConductorServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conductores")
@RequiredArgsConstructor
public class ConductorController {

    private final ConductorServiceI conductorService;

    @GetMapping("/{id}")
    public ResponseEntity<Conductor> obtenerConductor(@PathVariable Long id) {
        return ResponseEntity.ok(conductorService.obtenerConductor(id));
    }

    @PostMapping("/{conductorId}/vehiculo")
    public ResponseEntity<Void> agregarVehiculo(@PathVariable Long conductorId, @RequestBody Vehiculo vehiculo) {
        conductorService.agregarVehiculo(conductorId, vehiculo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{conductorId}/vehiculo/{vehiculoId}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long conductorId, @PathVariable Long vehiculoId) {
        conductorService.eliminarVehiculo(conductorId, vehiculoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{conductorId}/viajes")
    public ResponseEntity<List<Viaje>> verViajesPublicados(@PathVariable Long conductorId) {
        return ResponseEntity.ok(conductorService.verViajesPublicados(conductorId));
    }

    @GetMapping("/{conductorId}/valoracion")
    public ResponseEntity<Double> calcularValoracionMedia(@PathVariable Long conductorId) {
        return ResponseEntity.ok(conductorService.calcularValoracionMedia(conductorId));
    }
}
