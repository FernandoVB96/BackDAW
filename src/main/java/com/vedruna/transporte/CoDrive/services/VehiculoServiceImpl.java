package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoServiceI {

    private final VehiculoRepository vehiculoRepository;

    @Override
    public Vehiculo agregarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo obtenerVehiculo(Long id) {
        return vehiculoRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }

    @Override
    public void eliminarVehiculo(Long id) {
        Vehiculo vehiculo = obtenerVehiculo(id);
        vehiculoRepository.delete(vehiculo);
    }
    @Override
    public List<Vehiculo> obtenerVehiculosPorConductor(Long conductorId) {
        return vehiculoRepository.findByConductorId(conductorId);
    }

    @Override
    public void actualizarVehiculo(Long id, Vehiculo vehiculo) {
        Vehiculo vehiculoExistente = obtenerVehiculo(id);
        vehiculoExistente.actualizarInfo(vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getMatricula(), vehiculo.getPlazasDisponibles());
        vehiculoRepository.save(vehiculoExistente);
    }
}
