package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.dto.ViajeConRolDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.repository.ReservaRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.ViajeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeServiceImpl implements ViajeServiceI {

    private final ViajeRepository viajeRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaRepository reservaRepository;

    @Override
    public Viaje crearViaje(Viaje viaje) {
        Usuario conductor = obtenerUsuarioLogueado();
        viaje.setConductor(conductor);
        return viajeRepository.save(viaje);
    }
    @Override
    public List<Viaje> buscarPorFiltros(String origen, String destino, Integer plazasMin) {
        return viajeRepository.findByFiltros(origen, destino, plazasMin);
    }


    @Override
    public List<Viaje> obtenerViajesPorConductor(Long conductorId) {
        return viajeRepository.findByConductorId(conductorId);
    }

    @Override
    public List<Viaje> buscarPorDestino(String destino) {
        return viajeRepository.findByDestino(destino);
    }

    @Override
    public List<Viaje> buscarPorOrigenYDestino(String origen, String destino) {
        return viajeRepository.findByOrigenAndDestino(origen, destino);
    }

    @Override
    public List<Viaje> buscarPorPlazasDisponibles() {
        return viajeRepository.findByPlazasDisponiblesGreaterThan(0);
    }

    @Override
    public List<ViajeConRolDTO> buscarMisViajes() {
        Usuario usuarioLogueado = obtenerUsuarioLogueado();

        List<Viaje> viajesConductor = viajeRepository.findByConductor(usuarioLogueado);
        List<Viaje> viajesPasajero = viajeRepository.findByPasajeros(usuarioLogueado);

        List<ViajeConRolDTO> resultado = new ArrayList<>();

        for (Viaje viaje : viajesConductor) {
            resultado.add(new ViajeConRolDTO(viaje, "CONDUCTOR"));
        }

        for (Viaje viaje : viajesPasajero) {
            // Evita duplicados si el usuario es conductor y pasajero (raro pero posible)
            if (viajesConductor.stream().noneMatch(v -> v.getId().equals(viaje.getId()))) {
                resultado.add(new ViajeConRolDTO(viaje, "PASAJERO"));
            }
        }

        return resultado;
    }

    @Override
    @Transactional
    public Viaje obtenerViajePorId(Long id) {
        return viajeRepository.findByIdWithConductor(id)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
    }

    @Override
    @Transactional
    public void cancelarViaje(Long viajeId) {
        // 1. Borrar las reservas asociadas al viaje
        reservaRepository.deleteByViajeId(viajeId);

        // 2. Borrar el viaje
        viajeRepository.deleteById(viajeId);
    }
    @Override
    public Viaje unirseAViaje(Long viajeId) {
        Usuario usuario = obtenerUsuarioLogueado();
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        if (viaje.getPlazasDisponibles() <= 0) {
            throw new IllegalStateException("No hay plazas disponibles");
        }

        viaje.getPasajeros().add(usuario);
        viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() - 1);
        return viajeRepository.save(viaje);
    }

    @Override
    public Viaje abandonarViaje(Long viajeId) {
        Usuario usuario = obtenerUsuarioLogueado();
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        if (!viaje.getPasajeros().contains(usuario)) {
            throw new IllegalStateException("No estás en este viaje");
        }

        viaje.getPasajeros().remove(usuario);
        viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() + 1);
        return viajeRepository.save(viaje);
    }

    @Override
    public List<Usuario> obtenerPasajerosDeViaje(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
        return viaje.getPasajeros();
    }

    private Usuario obtenerUsuarioLogueado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
