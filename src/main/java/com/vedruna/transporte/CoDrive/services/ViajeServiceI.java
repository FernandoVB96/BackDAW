package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;

import java.util.List;

public interface ViajeServiceI {

    Viaje crearViaje(Viaje viaje);
    List<Viaje> obtenerViajesPorConductor(Long conductorId);
    List<Viaje> buscarPorDestino(String destino);
    List<Viaje> buscarPorOrigenYDestino(String origen, String destino);
    List<Viaje> buscarPorPlazasDisponibles();
    List<Viaje> buscarMisViajes();
    Viaje obtenerViajePorId(Long id);
    void cancelarViaje(Long viajeId);
    Viaje unirseAViaje(Long viajeId);
    Viaje abandonarViaje(Long viajeId);
    List<Usuario> obtenerPasajerosDeViaje(Long viajeId);
    List<Viaje> buscarPorFiltros(String origen, String destino, Integer plazasMin);

}
