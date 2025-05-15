package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;

public interface ConductorServiceI {

    void agregarVehiculo(Long usuarioId, Vehiculo vehiculo);

    void eliminarVehiculo(Long usuarioId, Long vehiculoId);

    double calcularValoracionMedia(Long usuarioId);
}
