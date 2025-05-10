package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Conductor;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.repository.ConductorRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConductorServiceImpl implements ConductorServiceI {

    private final ConductorRepository conductorRepository;
    private final VehiculoRepository vehiculoRepository;

    @Override
    public Conductor obtenerConductor(Long id) {
        return conductorRepository.findById(id).orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
    }

    @Override
    public void agregarVehiculo(Long conductorId, Vehiculo vehiculo) {
        Conductor conductor = obtenerConductor(conductorId);
        conductor.agregarVehiculo(vehiculo);
        vehiculo.setConductor(conductor);
        vehiculoRepository.save(vehiculo);
        conductorRepository.save(conductor);
    }

    @Override
    public void eliminarVehiculo(Long conductorId, Long vehiculoId) {
        Conductor conductor = obtenerConductor(conductorId);
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        conductor.eliminarVehiculo(vehiculo);
        vehiculoRepository.delete(vehiculo);
        conductorRepository.save(conductor);
    }

    @Override
    public List<Viaje> verViajesPublicados(Long conductorId) {
        // Aquí podrías agregar la lógica para obtener los viajes del conductor
        return null;
    }

    @Override
    public double calcularValoracionMedia(Long conductorId) {
        Conductor conductor = obtenerConductor(conductorId);
        return conductor.calcularValoracionMedia();
    }
}
