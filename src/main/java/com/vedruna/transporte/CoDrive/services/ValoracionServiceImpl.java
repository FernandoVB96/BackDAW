package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.dto.CrearValoracionDTO;
import com.vedruna.transporte.CoDrive.dto.UsuarioDTO;
import com.vedruna.transporte.CoDrive.dto.ValoracionResponseDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.ValoracionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValoracionServiceImpl implements ValoracionServiceI {

    private final ValoracionRepository valoracionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Valoracion crearValoracion(CrearValoracionDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario autor = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Usuario conductor = usuarioRepository.findById(dto.getConductorId())
                .orElseThrow(() -> new UsernameNotFoundException("Conductor no encontrado"));

        Valoracion valoracion = new Valoracion();
        valoracion.setPuntuacion(dto.getPuntuacion());
        valoracion.setComentario(dto.getComentario());
        valoracion.setFecha(dto.getFecha());
        valoracion.setAutor(autor);
        valoracion.setConductor(conductor);

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
