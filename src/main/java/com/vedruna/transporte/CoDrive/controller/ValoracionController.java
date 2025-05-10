package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;
import com.vedruna.transporte.CoDrive.services.ValoracionServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valoraciones")
@RequiredArgsConstructor
public class ValoracionController {

    private final ValoracionServiceI valoracionService;

    @PostMapping
    public ResponseEntity<Valoracion> crearValoracion(@RequestBody Valoracion valoracion) {
        Valoracion nuevaValoracion = valoracionService.crearValoracion(valoracion);
        return ResponseEntity.ok(nuevaValoracion);
    }

    @GetMapping("/conductor/{conductorId}")
    public ResponseEntity<List<Valoracion>> obtenerValoracionesPorConductor(@PathVariable Long conductorId) {
        return ResponseEntity.ok(valoracionService.obtenerValoracionesPorConductor(conductorId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Valoracion>> obtenerValoracionesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(valoracionService.obtenerValoracionesPorUsuario(usuarioId));
    }

    @GetMapping("/conductor/{conductorId}/media")
    public ResponseEntity<Double> calcularValoracionMedia(@PathVariable Long conductorId) {
        return ResponseEntity.ok(valoracionService.calcularValoracionMedia(conductorId));
    }
}
