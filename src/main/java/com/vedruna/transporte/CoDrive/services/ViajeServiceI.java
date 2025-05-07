package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import java.util.List;

public interface ViajeServiceI {

    // Método para crear un viaje
    Viaje crearViaje(Viaje viaje);

    // Método para buscar viajes por destino
    List<Viaje> buscarPorDestino(String destino);

    // Método para buscar viajes por origen y destino
    List<Viaje> buscarPorOrigenYDestino(String origen, String destino);

    // Método para obtener los viajes con plazas disponibles
    List<Viaje> buscarPorPlazasDisponibles();

    // Método para obtener los viajes del usuario logueado (como conductor o pasajero)
    List<Viaje> buscarMisViajes();
}
