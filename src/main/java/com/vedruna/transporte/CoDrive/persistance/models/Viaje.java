package com.vedruna.transporte.CoDrive.persistance.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;

    private LocalDateTime fechaHoraSalida;
    private LocalDateTime fechaHoraLlegada;

    private int plazasTotales;
    private int plazasDisponibles;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Usuario conductor;

    @ManyToMany
    @JoinTable(
        name = "viaje_pasajeros",
        joinColumns = @JoinColumn(name = "viaje_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @Builder.Default
    private List<Usuario> pasajeros = new ArrayList<>();
}
