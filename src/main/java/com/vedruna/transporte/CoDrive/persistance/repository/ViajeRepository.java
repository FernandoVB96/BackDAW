package com.vedruna.transporte.CoDrive.persistance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    List<Viaje> findByDestino(String destino);
    List<Viaje> findByOrigenAndDestino(String origen, String destino);
    List<Viaje> findByPlazasDisponiblesGreaterThan(int plazasDisponibles);
    List<Viaje> findByConductor(Usuario conductor);
    List<Viaje> findByPasajeros(Usuario usuario);
    List<Viaje> findByConductorId(Long conductorId);
    @Query("SELECT v FROM Viaje v JOIN FETCH v.conductor WHERE v.id = :id")
    Optional<Viaje> findByIdWithConductor(@Param("id") Long id);

    @Query("SELECT v FROM Viaje v WHERE " +
       "(:origen IS NULL OR v.origen = :origen) AND " +
       "(:destino IS NULL OR v.destino = :destino) AND " +
       "(:plazasMin IS NULL OR v.plazasDisponibles >= :plazasMin)")
    List<Viaje> findByFiltros(@Param("origen") String origen, 
                          @Param("destino") String destino, 
                          @Param("plazasMin") Integer plazasMin);

}
