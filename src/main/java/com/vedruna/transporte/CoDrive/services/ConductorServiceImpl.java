package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.VehiculoRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.ValoracionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConductorServiceImpl implements ConductorServiceI {

    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ValoracionRepository valoracionRepository;

    @Override
    public void agregarVehiculo(Long usuarioId, Vehiculo vehiculo) {
        Usuario conductor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
        if (!conductor.getRol().esConductor()) {
            throw new IllegalArgumentException("El usuario no es un conductor");
        }
        vehiculo.setConductor(conductor);
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void eliminarVehiculo(Long usuarioId, Long vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        if (!vehiculo.getConductor().getId().equals(usuarioId)) {
            throw new IllegalStateException("Este vehículo no pertenece al conductor");
        }
        vehiculoRepository.delete(vehiculo);
    }

    @Override
    public double calcularValoracionMedia(Long usuarioId) {
        Usuario conductor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!conductor.getRol().esConductor()) {
            throw new IllegalArgumentException("El usuario no es un conductor");
        }
        List<Valoracion> valoraciones = valoracionRepository.findByConductor(conductor);
        return valoraciones.stream()
                .mapToDouble(Valoracion::getPuntuacion)
                .average()
                .orElse(0.0);
    }
}
