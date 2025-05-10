package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;

import java.util.List;

public interface VehiculoServiceI {
    Vehiculo agregarVehiculo(Vehiculo vehiculo);
    Vehiculo obtenerVehiculo(Long id);
    void eliminarVehiculo(Long id);
    List<Vehiculo> obtenerVehiculosPorConductor(Long conductorId);
    void actualizarVehiculo(Long id, Vehiculo vehiculo);
}
