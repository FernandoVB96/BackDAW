package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.CrearViajeRequest;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.services.ViajeServiceI;
import com.vedruna.transporte.CoDrive.exceptions.NoEsConductorException; // Importamos la excepción personalizada
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viajes")
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeServiceI viajeService;

    // Método para crear un nuevo viaje
    @PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody CrearViajeRequest request) {
        // Obtener el usuario autenticado
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verificar si el usuario tiene el rol "CONDUCTOR"
        if (!userDetails.getAuthorities().toString().contains("ROLE_CONDUCTOR")) {
            throw new NoEsConductorException("Solo los conductores pueden crear viajes"); // Lanzar la excepción personalizada
        }

        // Crear el nuevo viaje
        Viaje viaje = new Viaje();
        viaje.setOrigen(request.getOrigen());
        viaje.setDestino(request.getDestino());
        viaje.setFechaHoraSalida(request.getFechaHoraSalida());
        viaje.setFechaHoraLlegada(request.getFechaHoraLlegada());
        viaje.setPlazasTotales(request.getPlazasTotales());
        viaje.setPlazasDisponibles(request.getPlazasTotales());

        // Asignar al conductor (usuario logueado) al viaje
        Usuario conductor = new Usuario();
        conductor.setEmail(userDetails.getUsername()); // Asignar el email del conductor (usuario logueado)
        viaje.setConductor(conductor);

        // Guardar el viaje en la base de datos
        Viaje nuevoViaje = viajeService.crearViaje(viaje);
        return ResponseEntity.ok(nuevoViaje);
    }

    // Método para obtener los viajes disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Viaje>> verDisponibles() {
        List<Viaje> viajesDisponibles = viajeService.buscarPorPlazasDisponibles();
        return ResponseEntity.ok(viajesDisponibles);
    }

    // Método para obtener los viajes del usuario logueado
    @GetMapping("/mis-viajes")
    public ResponseEntity<List<Viaje>> verMisViajes() {
        List<Viaje> misViajes = viajeService.buscarMisViajes();
        return ResponseEntity.ok(misViajes);
    }
}
