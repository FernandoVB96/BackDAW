package com.vedruna.transporte.CoDrive.persistance.repository;

import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    // Métodos personalizados si se necesitan
}
