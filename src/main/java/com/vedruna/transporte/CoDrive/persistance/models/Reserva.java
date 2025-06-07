package com.vedruna.transporte.CoDrive.persistance.models;

import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "Debe especificarse un usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "viaje_id")
    @NotNull(message = "Debe especificarse un viaje")
    private Viaje viaje;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "La fecha de reserva no puede ser nula")
    private Date fechaReserva;

    @NotBlank(message = "El estado de la reserva no puede estar vacío")
    @Pattern(
        regexp = "^(PENDIENTE|CONFIRMADA|CANCELADA)$",
        message = "El estado debe ser PENDIENTE, CONFIRMADA o CANCELADA"
    )
    private String estado;

    public void confirmar() {
        this.estado = "CONFIRMADA";
    }

    public void cancelar() {
        this.estado = "CANCELADA";
    }

    public boolean esModificable() {
        return !("CANCELADA".equalsIgnoreCase(estado) || "CONFIRMADA".equalsIgnoreCase(estado));
    }
}
