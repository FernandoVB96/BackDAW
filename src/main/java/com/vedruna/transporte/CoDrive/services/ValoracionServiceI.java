package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;

import java.util.List;

public interface ValoracionServiceI {
    Valoracion crearValoracion(Valoracion valoracion);
    List<Valoracion> obtenerValoracionesPorConductor(Long conductorId);
    List<Valoracion> obtenerValoracionesPorUsuario(Long usuarioId);
    double calcularValoracionMedia(Long conductorId);
}
