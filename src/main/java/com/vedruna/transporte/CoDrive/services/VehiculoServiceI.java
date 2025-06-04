package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.dto.VehiculoCreateDTO;
import com.vedruna.transporte.CoDrive.dto.VehiculoDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;

import java.util.List;

public interface VehiculoServiceI {
    Vehiculo agregarVehiculo(VehiculoCreateDTO dto);
    Vehiculo obtenerVehiculo(Long id);
    void eliminarVehiculo(Long id);
    List<Vehiculo> obtenerVehiculosPorConductor(Long conductorId);
    void actualizarVehiculo(Long id, VehiculoDTO vehiculoDTO);
}
