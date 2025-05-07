package com.vedruna.transporte.CoDrive.persistance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    List<Viaje> findByDestino(String destino);
    List<Viaje> findByOrigenAndDestino(String origen, String destino);
    List<Viaje> findByPlazasDisponiblesGreaterThan(int plazasDisponibles);
    List<Viaje> findByConductor(Usuario conductor);
    List<Viaje> findByPasajeros(Usuario usuario);

}

