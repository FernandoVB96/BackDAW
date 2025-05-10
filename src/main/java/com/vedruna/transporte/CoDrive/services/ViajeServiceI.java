package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import java.util.List;

public interface ViajeServiceI {

    // Crear un viaje
    Viaje crearViaje(Viaje viaje);

    // Buscar viajes por destino
    List<Viaje> buscarPorDestino(String destino);

    // Buscar viajes por origen y destino
    List<Viaje> buscarPorOrigenYDestino(String origen, String destino);

    // Buscar viajes con plazas disponibles
    List<Viaje> buscarPorPlazasDisponibles();

    // Obtener los viajes del usuario logueado
    List<Viaje> buscarMisViajes();

    // Cancelar un viaje (solo si es el conductor)
    void cancelarViaje(Long viajeId);

    // Unirse a un viaje como pasajero
    Viaje unirseAViaje(Long viajeId);

    // Abandonar un viaje (cancelar participación)
    Viaje abandonarViaje(Long viajeId);

    // Obtener los pasajeros de un viaje
    List<Usuario> obtenerPasajerosDeViaje(Long viajeId);
}
