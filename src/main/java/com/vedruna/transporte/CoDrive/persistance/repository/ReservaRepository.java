package com.vedruna.transporte.CoDrive.persistance.repository;

import com.vedruna.transporte.CoDrive.persistance.models.Reserva;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByViajeId(Long viajeId);
    List<Reserva> findByViajeIn(List<Viaje> viajes);
    void deleteByViajeId(Long viajeId);


}
