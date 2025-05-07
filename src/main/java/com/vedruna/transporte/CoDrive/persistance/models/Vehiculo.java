package com.vedruna.transporte.CoDrive.persistance.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String matricula;
    private int plazasDisponibles;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;
}

