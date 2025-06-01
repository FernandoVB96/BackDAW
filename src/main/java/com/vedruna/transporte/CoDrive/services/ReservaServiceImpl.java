package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.repository.ReservaRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaServiceI {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ViajeRepository viajeRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Reserva> obtenerReservasPorConductor(Long conductorId) {
        List<Viaje> viajesConductor = viajeRepository.findByConductorId(conductorId);
        return reservaRepository.findByViajeIn(viajesConductor);
    }


    @Override
    public List<Reserva> obtenerReservasPorViaje(Long viajeId) {
        return reservaRepository.findByViajeId(viajeId);
    }

    @Override
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setFechaReserva(reservaActualizada.getFechaReserva());
        reserva.setEstado(reservaActualizada.getEstado());

        // Actualizar usuario y viaje con objetos completos
        Long usuarioId = reservaActualizada.getUsuario().getId();
        Long viajeId = reservaActualizada.getViaje().getId();

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        reserva.setUsuario(usuario);
        reserva.setViaje(viaje);

        return reservaRepository.save(reserva);
    }

    @Override
    public void confirmarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.confirmar();
        reservaRepository.save(reserva);
    }

    @Override
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.cancelar();
        reservaRepository.save(reserva);
    }
}
