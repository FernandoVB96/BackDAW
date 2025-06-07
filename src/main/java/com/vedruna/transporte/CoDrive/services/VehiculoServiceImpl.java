package com.vedruna.transporte.CoDrive.services;

import com.vedruna.transporte.CoDrive.dto.VehiculoCreateDTO;
import com.vedruna.transporte.CoDrive.dto.VehiculoDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Vehiculo;
import com.vedruna.transporte.CoDrive.persistance.repository.UsuarioRepository;
import com.vedruna.transporte.CoDrive.persistance.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoServiceI {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Vehiculo agregarVehiculo(VehiculoCreateDTO dto) {
        Usuario conductor = usuarioRepository.findById(dto.getConductorId())
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setMatricula(dto.getMatricula());
        vehiculo.setPlazasDisponibles(dto.getPlazasDisponibles());
        vehiculo.setConductor(conductor);

        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo obtenerVehiculo(Long id) {
        return vehiculoRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
    }

    @Override
    public void eliminarVehiculo(Long id) {
        Vehiculo vehiculo = obtenerVehiculo(id);
        vehiculoRepository.delete(vehiculo);
    }
    @Override
    public List<Vehiculo> obtenerVehiculosPorConductor(Long conductorId) {
        return vehiculoRepository.findByConductorId(conductorId);
    }

    @Override
    public void actualizarVehiculo(Long id, VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculoExistente = obtenerVehiculo(id);
        vehiculoExistente.setMarca(vehiculoDTO.getMarca());
        vehiculoExistente.setModelo(vehiculoDTO.getModelo());
        vehiculoExistente.setMatricula(vehiculoDTO.getMatricula());
        vehiculoExistente.setPlazasDisponibles(vehiculoDTO.getPlazasDisponibles());
        vehiculoRepository.save(vehiculoExistente);
    }
}
