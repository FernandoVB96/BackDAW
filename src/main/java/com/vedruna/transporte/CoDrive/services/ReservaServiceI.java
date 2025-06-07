package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;

import java.util.List;
import java.util.Optional;

public interface ReservaServiceI {
    Reserva crearReserva(Reserva reserva);
    Reserva actualizarReserva(Long id, Reserva reserva);
    void eliminarReserva(Long id);
    Optional<Reserva> obtenerReservaPorId(Long id);
    List<Reserva> obtenerReservasPorUsuario(Long usuarioId);
    List<Reserva> obtenerReservasPorViaje(Long viajeId);
    void confirmarReserva(Long id);
    void cancelarReserva(Long id);
    List<Reserva> obtenerReservasPorConductor(Long conductorId);

}
