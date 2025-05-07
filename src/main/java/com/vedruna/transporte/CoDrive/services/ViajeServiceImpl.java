package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.persistance.models.Viaje;
import com.vedruna.transporte.CoDrive.persistance.repository.ViajeRepository;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeServiceImpl implements ViajeServiceI {

    private final ViajeRepository viajeRepository;

    // Crear viaje (sin cambios)
    @Override
    public Viaje crearViaje(Viaje viaje) {
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
        // Aquí deberías obtener al usuario logueado usando tu lógica de autenticación
        // Por ejemplo, si usas Spring Security, puedes usar algo como:
        // return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Usuario(); // Esto es solo un placeholder
    }
}
