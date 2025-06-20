package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.VehiculoCreateDTO;
import com.vedruna.transporte.CoDrive.dto.VehiculoDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.services.VehiculoServiceI;

import jakarta.validation.Valid;
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
    public ResponseEntity<Vehiculo> agregarVehiculo(@Valid @RequestBody VehiculoCreateDTO vehiculoDTO) {
        Vehiculo nuevoVehiculo = vehiculoService.agregarVehiculo(vehiculoDTO);
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
    public ResponseEntity<Void> actualizarVehiculo(@PathVariable Long id, @Valid @RequestBody VehiculoDTO vehiculoDTO) {
        vehiculoService.actualizarVehiculo(id, vehiculoDTO);
        return ResponseEntity.noContent().build();
    }
}
