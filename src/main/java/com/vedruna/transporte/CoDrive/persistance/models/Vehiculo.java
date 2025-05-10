package com.vedruna.transporte.CoDrive.persistance.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String matricula;
    private int plazasDisponibles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    // Método para verificar si el vehículo está disponible
    public boolean estaDisponible() {
        return plazasDisponibles > 0;
    }

    // Método para actualizar la información del vehículo
    public void actualizarInfo(String marca, String modelo, String matricula, int plazas) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.plazasDisponibles = plazas;
    }
}
