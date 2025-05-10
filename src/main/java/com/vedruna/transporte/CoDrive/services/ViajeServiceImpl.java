package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.repository.ViajeRepository;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeServiceImpl implements ViajeServiceI {

    private final ViajeRepository viajeRepository;
    private final UsuarioRepository usuarioRepository; 

    // Crear viaje (sin cambios)
    @Override
    public Viaje crearViaje(Viaje viaje) {
        Usuario conductor = obtenerUsuarioLogueado();
        viaje.setConductor(conductor);
        return viajeRepository.save(viaje);
    }
    

    // Buscar viajes por destino (sin cambios)
    @Override
    public List<Viaje> buscarPorDestino(String destino) {
        return viajeRepository.findByDestino(destino);
    }

    // Buscar viajes por origen y destino (sin cambios)
    @Override
    public List<Viaje> buscarPorOrigenYDestino(String origen, String destino) {
        return viajeRepository.findByOrigenAndDestino(origen, destino);
    }

    // Modificado: Buscar viajes con plazas disponibles
    public List<Viaje> buscarPorPlazasDisponibles() {
        // Suponiendo que los viajes con plazas disponibles se filtran con una condición en el repositorio
        return viajeRepository.findByPlazasDisponiblesGreaterThan(0); // Esto supone que tienes un método en el repositorio para esto
    }

    // Modificado: Buscar los viajes del usuario logueado
    public List<Viaje> buscarMisViajes() {
        // Aquí necesitas obtener al usuario logueado. Puedes hacerlo a través de un servicio de autenticación, por ejemplo.
        Usuario usuarioLogueado = obtenerUsuarioLogueado(); // Esta es una función ficticia, tienes que implementarla

        // Buscar los viajes del usuario (como conductor o pasajero)
        List<Viaje> viajesConductor = viajeRepository.findByConductor(usuarioLogueado);
        List<Viaje> viajesPasajero = viajeRepository.findByPasajeros(usuarioLogueado);

        // Unir ambos resultados
        viajesConductor.addAll(viajesPasajero);
        return viajesConductor;
    }

    // Método ficticio para obtener el usuario logueado
    private Usuario obtenerUsuarioLogueado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public void cancelarViaje(Long viajeId) {
        Usuario conductor = obtenerUsuarioLogueado();
        Viaje viaje = viajeRepository.findById(viajeId)
            .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
        
        if (!viaje.getConductor().getId().equals(conductor.getId())) {
            throw new SecurityException("No tienes permiso para cancelar este viaje");
        }
    
        viajeRepository.delete(viaje);
    }

    public Viaje unirseAViaje(Long viajeId) {
        Usuario usuario = obtenerUsuarioLogueado();
        Viaje viaje = viajeRepository.findById(viajeId)
            .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
    
        if (viaje.getPlazasDisponibles() <= 0) {
            throw new IllegalStateException("No hay plazas disponibles");
        }
    
        viaje.getPasajeros().add(usuario);
        viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() - 1);
        return viajeRepository.save(viaje);
    }
    
    public Viaje abandonarViaje(Long viajeId) {
        Usuario usuario = obtenerUsuarioLogueado();
        Viaje viaje = viajeRepository.findById(viajeId)
            .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
    
        if (!viaje.getPasajeros().contains(usuario)) {
            throw new IllegalStateException("No estás en este viaje");
        }
    
        viaje.getPasajeros().remove(usuario);
        viaje.setPlazasDisponibles(viaje.getPlazasDisponibles() + 1);
        return viajeRepository.save(viaje);
    }
    
    public List<Usuario> obtenerPasajerosDeViaje(Long viajeId) {
        Viaje viaje = viajeRepository.findById(viajeId)
            .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));
        return viaje.getPasajeros();
    }
    
    
    
}
