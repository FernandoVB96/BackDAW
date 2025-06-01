package com.vedruna.transporte.CoDrive.persistance.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @NotBlank(message = "La matrícula no puede estar vacía")
    private String matricula;

    @Min(value = 1, message = "Debe haber al menos una plaza disponible")
    private int plazasDisponibles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id")
    @JsonIgnore
    @NotNull(message = "Debe especificarse el conductor del vehículo")
    private Usuario conductor;

    public boolean estaDisponible() {
        return plazasDisponibles > 0;
    }

    public void actualizarInfo(String marca, String modelo, String matricula, int plazas) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.plazasDisponibles = plazas;
    }
}
