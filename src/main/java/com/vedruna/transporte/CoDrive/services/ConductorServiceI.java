package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Conductor;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;

import java.util.List;

public interface ConductorServiceI {
    Conductor obtenerConductor(Long id);
    void agregarVehiculo(Long conductorId, Vehiculo vehiculo);
    void eliminarVehiculo(Long conductorId, Long vehiculoId);
    List<Viaje> verViajesPublicados(Long conductorId);
    double calcularValoracionMedia(Long conductorId);
}
