package com.vedruna.transporte.CoDrive.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vedruna.transporte.CoDrive.persistance.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
