package com.vedruna.transporte.CoDrive.controller;

import com.vedruna.transporte.CoDrive.dto.CrearValoracionDTO;
import com.vedruna.transporte.CoDrive.dto.ValoracionResponseDTO;
import com.vedruna.transporte.CoDrive.dto.UsuarioDTO;
import com.vedruna.transporte.CoDrive.persistance.models.Usuario;
import com.vedruna.transporte.CoDrive.persistance.models.Valoracion;
import com.vedruna.transporte.CoDrive.services.ValoracionServiceI;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/valoraciones")
@RequiredArgsConstructor
public class ValoracionController {

    private final ValoracionServiceI valoracionService;

    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> crearValoracion(@Valid @RequestBody CrearValoracionDTO dto) {
        Valoracion valoracionCreada = valoracionService.crearValoracion(dto);
        ValoracionResponseDTO responseDTO = mapToValoracionResponseDTO(valoracionCreada);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/conductor/{conductorId}")
    public ResponseEntity<List<ValoracionResponseDTO>> obtenerValoracionesPorConductor(@PathVariable Long conductorId) {
        List<Valoracion> valoraciones = valoracionService.obtenerValoracionesPorConductor(conductorId);
        List<ValoracionResponseDTO> dtos = valoraciones.stream()
                .map(this::mapToValoracionResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ValoracionResponseDTO>> obtenerValoracionesPorUsuario(@PathVariable Long usuarioId) {
        List<Valoracion> valoraciones = valoracionService.obtenerValoracionesPorUsuario(usuarioId);
        List<ValoracionResponseDTO> dtos = valoraciones.stream()
                .map(this::mapToValoracionResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/conductor/{conductorId}/media")
    public ResponseEntity<Double> calcularValoracionMedia(@PathVariable Long conductorId) {
        return ResponseEntity.ok(valoracionService.calcularValoracionMedia(conductorId));
    }

    // Método para mapear Valoracion a ValoracionResponseDTO, excluyendo password y demás datos sensibles
    private ValoracionResponseDTO mapToValoracionResponseDTO(Valoracion valoracion) {
        ValoracionResponseDTO dto = new ValoracionResponseDTO();
        dto.setId(valoracion.getId());
        dto.setComentario(valoracion.getComentario());
        dto.setPuntuacion(valoracion.getPuntuacion());
        dto.setFecha(valoracion.getFecha());

        Usuario conductor = valoracion.getConductor();
        if (conductor != null) {
            UsuarioDTO conductorDTO = new UsuarioDTO();
            conductorDTO.setId(conductor.getId());
            conductorDTO.setNombre(conductor.getNombre());
            conductorDTO.setEmail(conductor.getEmail());
            conductorDTO.setTelefono(conductor.getTelefono());
            conductorDTO.setRol(conductor.getRol());
            // NO seteamos password, no queremos que salga nunca
            dto.setConductor(conductorDTO);
        }

        Usuario autor = valoracion.getAutor();
        if (autor != null) {
            UsuarioDTO autorDTO = new UsuarioDTO();
            autorDTO.setId(autor.getId());
            autorDTO.setNombre(autor.getNombre());
            autorDTO.setEmail(autor.getEmail());
            autorDTO.setTelefono(autor.getTelefono());
            autorDTO.setRol(autor.getRol());
            dto.setAutor(autorDTO);
        }

        return dto;
    }
}
