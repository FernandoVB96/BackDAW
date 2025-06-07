package com.vedruna.transporte.CoDrive.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vedruna.transporte.CoDrive.dto.AuthResponse;
import com.vedruna.transporte.CoDrive.dto.LoginRequest;
import com.vedruna.transporte.CoDrive.dto.RegistroRequest;
import com.vedruna.transporte.CoDrive.exceptions.EmailAlreadyExistsException;
import com.vedruna.transporte.CoDrive.persistance.models.Rol;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Modificación: Verificar si el usuario ya existe antes de registrarlo
    public AuthResponse registrar(@Valid @RequestBody RegistroRequest request) {
        // Verificar si el correo ya está registrado
        Usuario usuarioExistente = usuarioRepository.findByEmail(request.getEmail()).orElse(null);
        if (usuarioExistente != null) {
            throw new EmailAlreadyExistsException("El correo electrónico ya está registrado");
        }
    
        // Crear el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USUARIO);
    
        // Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(usuario);
    
        // Generar el JWT para el usuario recién registrado
        String jwt = jwtService.generateToken(usuario);
    
        return new AuthResponse(jwt);
    }
    
    
    // Login: Verificar las credenciales del usuario
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Verificar que las credenciales coinciden
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Generar y devolver el token JWT
        String jwt = jwtService.generateToken(usuario);
        return new AuthResponse(jwt);
    }
}
