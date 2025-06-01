package com.vedruna.transporte.CoDrive.persistance.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El origen no puede estar vacío")
    private String origen;

    @NotBlank(message = "El destino no puede estar vacío")
    private String destino;

    @NotNull(message = "La fecha y hora de salida no puede ser nula")
    @Future(message = "La salida debe ser una fecha futura")
    private LocalDateTime fechaHoraSalida;

    @NotNull(message = "La fecha y hora de llegada no puede ser nula")
    @Future(message = "La llegada debe ser una fecha futura")
    private LocalDateTime fechaHoraLlegada;

    @Min(value = 1, message = "Debe haber al menos una plaza total")
    private int plazasTotales;

    @Min(value = 0, message = "Las plazas disponibles no pueden ser negativas")
    private int plazasDisponibles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conductor_id")
    @NotNull(message = "Debe asignarse un conductor al viaje")
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
