package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.services.VehiculoServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoServiceI vehiculoService;

    @PostMapping
    public ResponseEntity<Vehiculo> agregarVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.agregarVehiculo(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculo(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.obtenerVehiculo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conductor/{conductorId}")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosPorConductor(@PathVariable Long conductorId) {
        return ResponseEntity.ok(vehiculoService.obtenerVehiculosPorConductor(conductorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        vehiculoService.actualizarVehiculo(id, vehiculo);
        return ResponseEntity.noContent().build();
    }
}
