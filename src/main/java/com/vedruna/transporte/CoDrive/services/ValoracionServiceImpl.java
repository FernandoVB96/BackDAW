package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;
import com.vedruna.transporte.CoDrive.persistance.repository.ValoracionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValoracionServiceImpl implements ValoracionServiceI {

    private final ValoracionRepository valoracionRepository;

    @Override
    public Valoracion crearValoracion(Valoracion valoracion) {
        if (!valoracion.esValida()) {
            throw new IllegalArgumentException("La valoración no es válida");
        }
        return valoracionRepository.save(valoracion);
    }

    @Override
    public List<Valoracion> obtenerValoracionesPorConductor(Long conductorId) {
        // Lógica para obtener todas las valoraciones de un conductor específico
        return valoracionRepository.findAll();  // Aquí podrías ajustar según las necesidades
    }

    @Override
    public List<Valoracion> obtenerValoracionesPorUsuario(Long usuarioId) {
        // Lógica para obtener todas las valoraciones de un usuario específico
        return valoracionRepository.findAll();  // Aquí podrías ajustar según las necesidades
    }

    @Override
    public double calcularValoracionMedia(Long conductorId) {
        List<Valoracion> valoraciones = obtenerValoracionesPorConductor(conductorId);
        return valoraciones.stream().mapToDouble(Valoracion::getPuntuacion).average().orElse(0.0);
    }
}
