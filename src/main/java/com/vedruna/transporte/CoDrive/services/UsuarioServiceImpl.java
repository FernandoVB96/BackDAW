package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.dto.ActualizarUsuarioDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioServiceI {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario obtenerMiPerfil() {
        String email = getEmailUsuarioLogueado();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Override
    public Usuario actualizarPerfil(ActualizarUsuarioDTO datosActualizados) {
        Usuario usuario = obtenerMiPerfil();

        usuario.setNombre(datosActualizados.getNombre());
        usuario.setTelefono(datosActualizados.getTelefono());
        usuario.setEmail(datosActualizados.getEmail());
        usuario.setRol(datosActualizados.getRol());

        if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(datosActualizados.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarMiCuenta() {
        Usuario usuario = obtenerMiPerfil();
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    private String getEmailUsuarioLogueado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
