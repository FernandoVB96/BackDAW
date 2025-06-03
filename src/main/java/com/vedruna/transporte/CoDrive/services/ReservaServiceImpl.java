package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.repository.ReservaRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaServiceI {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ViajeRepository viajeRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        Reserva creada = reservaRepository.save(reserva);

        // Obtener token Expo del conductor
        String expoPushToken = creada.getViaje().getConductor().getExpoPushToken();
        if (expoPushToken != null && !expoPushToken.isEmpty()) {
            enviarNotificacionExpo(
                expoPushToken,
                "Nueva reserva",
                "Tienes una nueva reserva para el viaje " + creada.getViaje().getOrigen() + " -> " + creada.getViaje().getDestino()
            );
        }
        return creada;
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

        // Agregar pasajero al viaje si no está ya
        Viaje viaje = reserva.getViaje();
        Usuario pasajero = reserva.getUsuario();

        if (!viaje.getPasajeros().contains(pasajero)) {
            viaje.getPasajeros().add(pasajero);
            viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() - 1);
            viajeRepository.save(viaje);
        }

        reserva.confirmar();
        reservaRepository.save(reserva);

        String expoPushTokenUsuario = pasajero.getExpoPushToken();
        if (expoPushTokenUsuario != null && !expoPushTokenUsuario.isEmpty()) {
            enviarNotificacionExpo(
                expoPushTokenUsuario,
                "Reserva confirmada",
                "Tu reserva para el viaje " + viaje.getOrigen() + " -> " + viaje.getDestino() + " fue confirmada"
            );
        }
    }


    @Override
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.cancelar();
        reservaRepository.save(reserva);

        String expoPushTokenUsuario = reserva.getUsuario().getExpoPushToken();
        if (expoPushTokenUsuario != null && !expoPushTokenUsuario.isEmpty()) {
            enviarNotificacionExpo(
                expoPushTokenUsuario,
                "Reserva cancelada",
                "Tu reserva para el viaje " + reserva.getViaje().getOrigen() + " -> " + reserva.getViaje().getDestino() + " fue cancelada"
            );
        }
    }

    private void enviarNotificacionExpo(String expoPushToken, String title, String body) {
        String url = "https://exp.host/--/api/v2/push/send";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> message = new HashMap<>();
        message.put("to", expoPushToken);
        message.put("sound", "default");
        message.put("title", title);
        message.put("body", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(message, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            System.out.println("Expo push response: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
