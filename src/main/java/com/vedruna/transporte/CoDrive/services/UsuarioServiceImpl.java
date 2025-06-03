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

        System.out.println("Actualizando usuario con datos: " + datosActualizados);

        usuario.setNombre(datosActualizados.getNombre());
        usuario.setTelefono(datosActualizados.getTelefono());
        usuario.setEmail(datosActualizados.getEmail());
        usuario.setRol(datosActualizados.getRol());

        String nuevaPassword = datosActualizados.getPassword();
        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            if (nuevaPassword.length() < 8) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
            }
            usuario.setPassword(passwordEncoder.encode(nuevaPassword));
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

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
