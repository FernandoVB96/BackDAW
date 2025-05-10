package com.vedruna.transporte.CoDrive.persistance.repository;

import com.vedruna.transporte.CoDrive.persistance.models.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
    // Métodos personalizados si se necesitan
}
