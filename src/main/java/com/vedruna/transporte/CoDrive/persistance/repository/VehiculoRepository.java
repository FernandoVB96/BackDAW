package com.vedruna.transporte.CoDrive.persistance.repository;

import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByConductorId(Long conductorId);
}
